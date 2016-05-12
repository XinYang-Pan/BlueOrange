package org.blueo.db.java;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.EnumUtils;
import org.apache.commons.lang3.StringUtils;
import org.blueo.db.config.DbTableConfig;
import org.blueo.db.config.raw.DbGlobalConfigRawData;
import org.blueo.db.config.raw.DbTableConfigRawData;
import org.blueo.db.vo.DbColumn;
import org.blueo.db.vo.DbEnum;
import org.blueo.db.vo.DbTable;
import org.blueo.db.vo.DbType;
import org.blueo.db.vo.raw.DbColumnRawData;

import com.google.common.base.Splitter;
import com.google.common.base.Throwables;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

public class DataLoader {
	private enum Type{Config, Enum, TypeMapping, Table}
	//
	private final String excelPath;
	// Internal process fields
	private DbGlobalConfigRawData dbConfig;
	private List<DbTable> dbTables = Lists.newArrayList();
	private List<DbEnum> dbEnums = Lists.newArrayList();;
	private Map<String, DbEnum> name2dbEnumsMap = Maps.newHashMap();
	private Map<String, String> rawType2SqlType = Maps.newHashMap();
	private DbTableConfigRawData defaultDbTableConfigRawData;
	
	private DataLoader(String excelPath) {
		this.excelPath = excelPath;
	}
	
	public static DataLoader build(String excelPath) {
		DataLoader loader = new DataLoader(excelPath);
		loader.load();
		return loader;
	}

	private void load() {
		try {
			Workbook workbook = Workbook.getWorkbook(new File(excelPath));
			Sheet[] sheets = workbook.getSheets();
			for (int i = 0; i < sheets.length; i++) {
				Sheet sheet = sheets[i];
				Map<String, String> key2Value = key2Value(sheet);
				Type type = this.getType(key2Value);
				if (type == null) {
					continue;
				}
				// switching type
				switch (type) {
				case Config:
					// config
					dbConfig = this.buildDbGlobalConfig(key2Value);
					defaultDbTableConfigRawData = this.buildDefaultDbTableConfigRawData(key2Value);
					break;
				case TypeMapping:
					// TypeMapping
					rawType2SqlType.putAll(key2Value);
					rawType2SqlType.remove("type");
					break;
				case Enum:
					// Enum
					this.buildEnumConfig(sheet, key2Value);
					break;
				case Table:
					// tables
					DbTable dbTable = this.buildDbTable(sheet, key2Value);
					if (dbTable != null) {
						dbTables.add(dbTable);
					}
					break;
				default:
					break;
				}
			}
		} catch (Exception e) {
			throw Throwables.propagate(e);
		}
	}

	private void buildEnumConfig(Sheet sheet, Map<String, String> key2Value) {
		Integer rowStartIndex = findRowIndex(sheet, 0, "definition");
		if (rowStartIndex == null) {
			return;
		}
		// 
		String packageName = key2Value.get("package");
		int rows = sheet.getRows();
		for (int i = rowStartIndex; i < rows; i++) {
			String enumName = getContent(sheet, 1, i);
			String enumValues = getContent(sheet, 2, i);
			if (StringUtils.isNotBlank(enumValues)) {
				// 
				DbEnum dbEnum = new DbEnum();
				dbEnum.setPackageName(packageName);
				dbEnum.setName(enumName);
				dbEnum.setValues(Splitter.on(',').trimResults().splitToList(enumValues));
				// 
				dbEnums.add(dbEnum);
				name2dbEnumsMap.put(dbEnum.getName(), dbEnum);
			}
		}
	}

	private DbGlobalConfigRawData buildDbGlobalConfig(Map<String, String> key2Value) throws IllegalAccessException, InvocationTargetException {
		// 
		DbGlobalConfigRawData dbGlobalConfigRawData = new DbGlobalConfigRawData();
		BeanUtils.populate(dbGlobalConfigRawData, key2Value);
		return dbGlobalConfigRawData;
	}

	private DbTableConfigRawData buildDefaultDbTableConfigRawData(Map<String, String> key2Value) throws IllegalAccessException, InvocationTargetException {
		// 
		DbTableConfigRawData defaultDbTableConfigRawData = new DbTableConfigRawData();
		BeanUtils.populate(defaultDbTableConfigRawData, key2Value);
		return defaultDbTableConfigRawData;
	}

	private DbTableConfig buildDbTableConfig(Map<String, String> key2Value) throws IllegalAccessException, InvocationTargetException {
		// 
		DbTableConfigRawData dbTableConfigRawData = new DbTableConfigRawData();
		BeanUtils.copyProperties(dbTableConfigRawData, defaultDbTableConfigRawData);
		BeanUtils.populate(dbTableConfigRawData, key2Value);
		return DataLoaderUtils.buildDbTableConfig(dbTableConfigRawData, rawType2SqlType);
	}

	private Type getType(Map<String, String> key2Value) {
		String enumName = key2Value.get("type");
		return EnumUtils.getEnum(Type.class, enumName);
	}
	
	private Map<String, String> key2Value(Sheet sheet) {
		Map<String, String> key2Value = Maps.newHashMap();
		int rows = sheet.getRows();
		for (int i = 0; i < rows; i++) {
			String key = getContent(sheet, 0, i);
			if (key != null) {
				String value = getContent(sheet, 1, i);
				key2Value.put(key, value);
			}
		}
		return key2Value;
	}
	
	private DbTable buildDbTable(Sheet sheet, Map<String, String> key2Value) throws IllegalAccessException, InvocationTargetException {
		return this.doBuildDbTable(sheet, key2Value);
	}
	
	private DbTable doBuildDbTable(Sheet sheet, Map<String, String> key2Value) throws IllegalAccessException, InvocationTargetException {
		String tableName = key2Value.get("name");
		if (StringUtils.isBlank(tableName)) {
			// TODO format exception
			return null;
		}
		String seq = key2Value.get("seq");
		// Find Table Definition start
		DbTableConfig dbTableConfig = buildDbTableConfig(key2Value);
		int rows = sheet.getRows();
		Integer rowStartIndex = findRowIndex(sheet, 0, "definition");
		if (rowStartIndex == null) {
			// TODO format exception
			return null;
		}
		// 
		DbColumn pk = null;
		int columnStartIdx = 1;
		List<DbColumn> dbColumns = Lists.newArrayList();
		// Column Name
		Map<Integer, String> indexColumnName = Maps.newHashMap();
		for (int i = columnStartIdx; i < sheet.getColumns(); i++) {
			String columnName = getContent(sheet, i, rowStartIndex);
			if (StringUtils.isNotBlank(columnName)) {
				// upper camel case to lower camel case
				indexColumnName.put(i, StringUtils.uncapitalize(columnName));
			}
		}
		// Column Value
		for (int i = rowStartIndex + 1; i < rows; i++) {
			Map<String, String> valueColumnName = Maps.newHashMap();
			for (int j = columnStartIdx; j < sheet.getColumns(); j++) {
				String content = getContent(sheet, j, i);
				if (StringUtils.isNotBlank(content)) {
					String columnName = indexColumnName.get(j);
					valueColumnName.put(columnName, content);
				}
			}
			DbColumnRawData dbColumnRawData = DataLoaderUtils.buildDbColumnRawData(valueColumnName);
			if (dbColumnRawData.getName() == null) {
				// Ignore no name object
				continue;
			}
			DataLoaderUtils.overwriteSqlSqlFromDefault(dbColumnRawData, rawType2SqlType);
			DbColumn dbcolumn = DataLoaderUtils.buildDbColumn(dbColumnRawData, name2dbEnumsMap);
			// 
			if (dbcolumn.isPk()) {
				pk = dbcolumn;
			} else {
				dbColumns.add(dbcolumn);
			}
		}
		// Table
		DbTable dbTable = new DbTable();
		dbTable.setName(tableName);
		if (StringUtils.isNotBlank(seq)) {
			dbTable.setSeq(seq);
		}
		dbTable.setPk(pk);
		dbTable.setDbColumns(dbColumns);
		dbTable.setDbTableConfig(dbTableConfig);
		// Traceable
		if (dbTableConfig.isTraceable()) {
			dbColumns.add(buildDbColumn("CREATE_ID", dbTableConfig.getTraceType()));
			dbColumns.add(buildDbColumn("UPDATE_ID", dbTableConfig.getTraceType()));
			dbColumns.add(buildDbColumn("CREATE_TIME", dbTableConfig.getTraceTimeType()));
			dbColumns.add(buildDbColumn("UPDATE_TIME", dbTableConfig.getTraceTimeType()));
		}
		if (dbTableConfig.isActiveable()) {
			dbColumns.add(buildDbColumn("ACTIVE_FLAG", dbTableConfig.getActiveableType()));
		}
		// HasId
		if (dbTableConfig.isHasId() && dbTable.getPk() == null) {
			DbColumn dbcolumn = new DbColumn();
			dbcolumn.setName("ID");
			dbcolumn.setDbType(dbTableConfig.getIdType());
			dbcolumn.setPk(true);
			dbcolumn.setNullable(false);
			dbcolumn.setComment("Auto added for HasId Po");
			dbTable.setPk(dbcolumn);
		}
		return dbTable;
	}

	private Integer findRowIndex(Sheet sheet, int columnIdx, String content) {
		int rows = sheet.getRows();
		for (int i = 0; i < rows; i++) {
			String key = getContent(sheet, columnIdx, i);
			if (content.equalsIgnoreCase(key)) {
				return i;
			}
		}
		return null;
	}

	private DbColumn buildDbColumn(String name, DbType type) {
		DbColumn dbcolumn = new DbColumn();
		dbcolumn.setName(name);
		dbcolumn.setDbType(type);
		dbcolumn.setPk(false);
		dbcolumn.setNullable(true);
		dbcolumn.setComment("Auto added for Traceable Po");
		return dbcolumn;
	}
	
	private String getContent(Sheet sheet, int column, int row) {
		if (column >= sheet.getColumns()) {return null;}
		if (row >= sheet.getRows()) {return null;}
		Cell cell = sheet.getCell(column, row);
		if (cell == null) {
			return null;
		}
		String contents = cell.getContents();
		if (StringUtils.isBlank(contents)) {
			return null;
		} else {
			return contents;
		}
	}

	public DbGlobalConfigRawData getDbConfig() {
		return dbConfig;
	}

	public List<DbTable> getDbTables() {
		return dbTables;
	}

	public List<DbEnum> getDbEnums() {
		return dbEnums;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("DataLoader [excelPath=");
		builder.append(excelPath);
		builder.append(", dbConfig=");
		builder.append(dbConfig);
		builder.append(", dbTables=");
		builder.append(dbTables);
		builder.append(", dbEnums=");
		builder.append(dbEnums);
		builder.append("]");
		return builder.toString();
	}
	
}
