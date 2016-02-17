package org.blueo.db.java;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.blueo.db.config.DbGlobalConfig;
import org.blueo.db.config.DbTableConfig;
import org.blueo.db.vo.DbColumn;
import org.blueo.db.vo.DbTable;

import com.google.common.base.Throwables;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

public class DataLoader {
	private enum Type{Config, Table}
	//
	private final String excelPath;
	// Internal process fields
	private DbGlobalConfig dbConfig;
	private List<DbTable> dbTables = Lists.newArrayList();
	
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

	private Type getType(Map<String, String> key2Value) {
		String content = key2Value.get("type");
		if (Type.Config.name().equalsIgnoreCase(content)) {
			return Type.Config;
		} else if (Type.Table.name().equalsIgnoreCase(content)) {
			return Type.Table;
		} else {
			return null;
		}
	}

	private DbGlobalConfig buildDbGlobalConfig(Map<String, String> key2Value) throws IllegalAccessException, InvocationTargetException {
		// 
		DbGlobalConfig dbGlobalConfig = new DbGlobalConfig();
		BeanUtils.populate(dbGlobalConfig, key2Value);
		dbGlobalConfig.setDbTableConfig(this.buildDbTableConfig(key2Value));
		return dbGlobalConfig;
	}

	private DbTableConfig buildDbTableConfig(Map<String, String> key2Value) throws IllegalAccessException, InvocationTargetException {
		// 
		DbTableConfig dbTableConfig = new DbTableConfig();
		if (dbConfig != null) {
			BeanUtils.copyProperties(dbTableConfig, dbConfig.getDbTableConfig());
		}
		BeanUtils.populate(dbTableConfig, key2Value);
		return dbTableConfig;
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
		// 
		DbTableConfig dbTableConfig = buildDbTableConfig(key2Value);
		int rows = sheet.getRows();
		Integer rowStartIndex = null;
		for (int i = 0; i < rows; i++) {
			String key = getContent(sheet, 0, i);
			if ("definition".equalsIgnoreCase(key)) {
				rowStartIndex = i;
			}
		}
		if (rowStartIndex == null) {
			// TODO format exception
			return null;
		}
		// 
		DbColumn pk = null;
		List<DbColumn> dbColumns = Lists.newArrayList();
		for (int i = rowStartIndex + 1; i < rows; i++) {
			String name = getContent(sheet, 1, i);
			if (StringUtils.isBlank(name)) {
				break;
			}
			DbColumn dbcolumn = new DbColumn();
			dbcolumn.setName(name.toUpperCase());
			dbcolumn.setType(getContent(sheet, 2, i));
			dbcolumn.setLength(getContent(sheet, 3, i));
			dbcolumn.setPk("y".equalsIgnoreCase(getContent(sheet, 4, i)));
			dbcolumn.setNullable("y".equalsIgnoreCase(ObjectUtils.firstNonNull(getContent(sheet, 5, i), "y")));
			dbcolumn.setComment(getContent(sheet, 6, i));
			//
			if (dbcolumn.isPk()) {
				pk = dbcolumn;
			} else {
				dbColumns.add(dbcolumn);
			}
		}
		//
		DbTable dbTable = new DbTable();
		dbTable.setName(tableName);
		dbTable.setPk(pk);
		dbTable.setDbColumns(dbColumns);
		dbTable.setDbTableConfig(dbTableConfig);
		// 
		if (dbTableConfig.isTraceableInBoolean()) {
			dbColumns.add(buildDbColumn("CREATE_ID", dbTableConfig.getTraceType()));
			dbColumns.add(buildDbColumn("UPDATE_ID", dbTableConfig.getTraceType()));
			dbColumns.add(buildDbColumn("CREATE_TIME", dbTableConfig.getTraceTimeType()));
			dbColumns.add(buildDbColumn("UPDATE_TIME", dbTableConfig.getTraceTimeType()));
			dbColumns.add(buildDbColumn("DEL_FLAG", dbTableConfig.getTraceDelFlagType()));
		}
		if (dbTableConfig.isHasIdInBoolean() && dbTable.getPk() == null) {
			DbColumn dbcolumn = new DbColumn();
			dbcolumn.setName("ID");
			dbcolumn.setType(dbTableConfig.getIdType());
			dbcolumn.setLength(null);
			dbcolumn.setPk(true);
			dbcolumn.setNullable(false);
			dbcolumn.setComment("Auto added for HasId Po");
			dbTable.setPk(dbcolumn);
		}
		return dbTable;
	}

	private DbColumn buildDbColumn(String name, String type) {
		DbColumn dbcolumn = new DbColumn();
		dbcolumn.setName(name);
		dbcolumn.setType(type);
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

	public DbGlobalConfig getDbConfig() {
		return dbConfig;
	}

	public List<DbTable> getDbTables() {
		return dbTables;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Loader [excelPath=");
		builder.append(excelPath);
		builder.append(", dbConfig=");
		builder.append(dbConfig);
		builder.append(", dbTables=");
		builder.append(dbTables);
		builder.append("]");
		return builder.toString();
	}
	
}
