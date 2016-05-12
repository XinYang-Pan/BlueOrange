package org.blueo.commons.persistent.jdbc;

import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.apache.commons.lang3.StringUtils;
import org.blueo.db.config.DbTableConfig;
import org.blueo.db.vo.DbColumn;
import org.blueo.db.vo.DbIndex;
import org.blueo.db.vo.DbTable;
import org.blueo.db.vo.DbType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.support.DatabaseMetaDataCallback;
import org.springframework.jdbc.support.JdbcUtils;
import org.springframework.jdbc.support.MetaDataAccessException;
import org.springframework.util.Assert;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

public class BlueoJdbcs {
	private static final String SEPARATOR = ", ";
	private static final Logger LOGGER = LoggerFactory.getLogger(BlueoJdbcs.class);

	public static String buildInsertSql(String tableName, List<String> columnNames) {
		String columnPart = StringUtils.join(columnNames, SEPARATOR);
		String valuePart = StringUtils.repeat("?", SEPARATOR, columnNames.size());
		return String.format("INSERT INTO %s(%s) VALUES(%s)", tableName, columnPart, valuePart);
	}

	public static String buildSeqInsertSql(String tableName, String seqName, String idColumnName, List<String> columnNames) {
		String columnPart = StringUtils.join(columnNames, SEPARATOR);
		String valuePart = StringUtils.repeat("?", SEPARATOR, columnNames.size());
		return String.format("INSERT INTO %s(%s, %s) VALUES(%s.NEXTVAL, %s)", tableName, idColumnName, columnPart, seqName, valuePart);
	}

	public static String buildUpdateSql(String tableName, List<String> columnNames, String idColumnName) {
		List<String> setPiece = Lists.newArrayList();
		for (String columnName : columnNames) {
			setPiece.add(String.format("%s=?", columnName));
		}
		String setPart = StringUtils.join(setPiece, SEPARATOR);
		return String.format("UPDATE %s SET %s WHERE %s=?", tableName, setPart, idColumnName);
	}

	public static String buildDeleteSql(String tableName, String idColumnName) {
		return String.format("DELETE FROM %s WHERE %s=?", tableName, idColumnName);
	}
	
	@SuppressWarnings("unchecked")
	public static List<DbTable> getDbTables(
			DataSource dataSource,
			final String catalog,
			final String schemaPattern,
			final String tableNamePattern) throws MetaDataAccessException {
		return (List<DbTable>)JdbcUtils.extractDatabaseMetaData(dataSource, new DatabaseMetaDataCallback() {

			@Override
			public List<DbTable> processMetaData(DatabaseMetaData dbmd) throws SQLException, MetaDataAccessException {
				ResultSet result = dbmd.getTables(catalog, schemaPattern, tableNamePattern, new String[] { "TABLE" });
				List<DbTable> dbTables = Lists.newArrayList();
				while (result.next()) {
					DbTable dbTable = new DbTable();
					dbTable.setDbTableConfig(new DbTableConfig());
					dbTable.setName(result.getString(3));
					dbTable.setPk(getPk(dbmd, dbTable.getName()));
					dbTable.setDbColumns(getDbColumns(dbmd, dbTable.getName()));
					dbTable.setDbIndexs(getIndexInfo(dbmd, dbTable.getName()));
					dbTables.add(dbTable);
				}
				return dbTables;
			}

		});
	}

	private static List<DbIndex> getIndexInfo(DatabaseMetaData dbmd, String name) throws SQLException {
		ResultSet result = dbmd.getIndexInfo(null, null, name, false, false);
		Map<String, DbIndex> map = Maps.newLinkedHashMap();
		while (result.next()) {
			boolean nonUnique = result.getBoolean(4);
			String indexName = result.getString(6);
			String columnName = result.getString(9);
			String ascOrDesc  = result.getString(10);
			// 
			if (indexName == null) {
				LOGGER.info("indexName is null. skipping ...");
				continue;
			}
			// 
			Assert.isTrue("A".equals(ascOrDesc), "Desc is not supported.");
			// 
			DbIndex dbIndex = map.get(indexName);
			if (dbIndex == null) {
				dbIndex = new DbIndex();
				dbIndex.setTableName(name);
				dbIndex.setIndexName(String.format("%s_%s", name, indexName));
				dbIndex.setColumnNames(Lists.<String>newArrayList());
				dbIndex.setUnique(!nonUnique);
				map.put(indexName, dbIndex);
			}
			dbIndex.getColumnNames().add(columnName);
		}
		return Lists.newArrayList(map.values());
	}

	private static List<DbColumn> getDbColumns(DatabaseMetaData dbmd, String name) throws SQLException {
		ResultSet result = dbmd.getColumns(null, null, name, "%");
		List<DbColumn> dbColumns = Lists.newArrayList();
		while (result.next()) {
			String columnName = result.getString(4);
//			int columnType = result.getInt(5);
			String typeName = result.getString(6);
			int columnSize = result.getInt(7);
//			int decimalSize = result.getInt(9);
			// 
			DbColumn dbColumn = new DbColumn();
			dbColumn.setName(columnName);
			dbColumn.setPk(false);
			dbColumn.setNullable(true);
			dbColumn.setEnumType(false);
			dbColumn.setDbType(getDbType(typeName, columnSize));
			dbColumns.add(dbColumn);
		}
		return dbColumns;
	}

	private static DbColumn getPk(DatabaseMetaData dbmd, String tableName) throws SQLException {
		ResultSet result = dbmd.getPrimaryKeys(null, null, tableName);
		while (result.next()) {
			String columnName = result.getString(4);
			String typeName = result.getString(6);
			int columnSize = result.getInt(7);
			DbColumn dbColumn = new DbColumn();
			dbColumn.setName(columnName);
			dbColumn.setPk(true);
			dbColumn.setNullable(true);
			dbColumn.setEnumType(false);
			dbColumn.setDbType(getDbType(typeName, columnSize));
			System.out.println("pk =" + dbColumn);
			return dbColumn;
		}
		return null;
	}

	private static DbType getDbType(String typeName, int columnSize) {
		switch (typeName) {
		case "int":
			return DbType.of("NUMBER", "10");
		case "char":
		case "varchar":
			return DbType.of("VARCHAR2", String.valueOf(columnSize));
		case "float":
			return DbType.of("FLOAT");
		case "datetime":
			return DbType.of("DATE");
		default:
			throw new IllegalArgumentException("unsupport "+typeName);
		}
	}
}
