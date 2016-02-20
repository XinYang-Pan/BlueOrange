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
import org.blueo.db.config.DbGlobalConfig;
import org.blueo.db.config.DbTableConfig;
import org.blueo.db.vo.DbColumn;
import org.blueo.db.vo.DbEnum;
import org.blueo.db.vo.DbTable;

import com.google.common.base.Splitter;
import com.google.common.base.Throwables;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

public class DataLoader {
	private enum Type{Config, Enum, Table}
	//
	private final String excelPath;
	// Internal process fields
	private DbGlobalConfig dbConfig;
	private List<DbTable> dbTables = Lists.newArrayList();
	private List<DbEnum> dbEnums = Lists.newArrayList();
	
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
				case Enum:
					// Enum
					dbEnums = this.buildEnumConfig(sheet);
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

	private List<DbEnum> buildEnumConfig(Sheet sheet) {
		Integer rowStartIndex = findRowIndex(sheet, 0, "definition");
		if (rowStartIndex == null) {
			return null;
		}
		List<DbEnum> dbEnums = Lists.newArrayList();
		// 
		int rows = sheet.getRows();
		for (int i = rowStartIndex; i < rows; i++) {
			String enumName = getContent(sheet, 1, i);
			String enumValues = getContent(sheet, 2, i);
			// 
			DbEnum dbEnum = new DbEnum();
			dbEnum.setName(enumName);
			dbEnum.setValues(Splitter.on(',').trimResults().splitToList(enumValues));
			dbEnums.add(dbEnum);
		}
		return dbEnums;
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
			DbColumn dbcolumn = new DbColumn();
			BeanUtils.populate(dbcolumn, valueColumnName);
			//
			if (dbcolumn.getName() == null) {
				// Ignore no name object
			} else if (dbcolumn.isPkInBool()) {
				pk = dbcolumn;
			} else {
				dbColumns.add(dbcolumn);
			}
		}
		// Table
		DbTable dbTable = new DbTable();
		dbTable.setName(tableName);
		dbTable.setPk(pk);
		dbTable.setDbColumns(dbColumns);
		dbTable.setDbTableConfig(dbTableConfig);
		// Traceable
		if (dbTableConfig.isTraceableInBoolean()) {
			dbColumns.add(buildDbColumn("CREATE_ID", dbTableConfig.getTraceType()));
			dbColumns.add(buildDbColumn("UPDATE_ID", dbTableConfig.getTraceType()));
			dbColumns.add(buildDbColumn("CREATE_TIME", dbTableConfig.getTraceTimeType()));
			dbColumns.add(buildDbColumn("UPDATE_TIME", dbTableConfig.getTraceTimeType()));
			dbColumns.add(buildDbColumn("DEL_FLAG", dbTableConfig.getTraceDelFlagType()));
		}
		// HasId
		if (dbTableConfig.isHasIdInBoolean() && dbTable.getPk() == null) {
			DbColumn dbcolumn = new DbColumn();
			dbcolumn.setName("ID");
			dbcolumn.setType(dbTableConfig.getIdType());
			dbcolumn.setLength(null);
			dbcolumn.setPkInBool(true);
			dbcolumn.setNullableInBool(false);
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
