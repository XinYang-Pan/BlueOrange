package org.blueo.db.java;

import java.io.File;
import java.util.List;
import java.util.Map;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.blueo.db.DbConfig;
import org.blueo.db.vo.DbColumn;
import org.blueo.db.vo.DbTable;
import org.javatuples.Pair;
import org.springframework.beans.BeanWrapperImpl;

import com.google.common.base.Throwables;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

public class DataLoader {
	private static Pair<Integer, Integer> TABLE_NAME_COORDINATE = Pair.with(1, 1);
	private static final int ROW_START_INDEX = 3;
	//
	private final String excelPath;
	// Internal process fields
	private DbConfig dbConfig;
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
				if ("config".equalsIgnoreCase(sheet.getName())) {
					// config
					dbConfig = this.buildDbConfig(sheet);
				} else {
					// tables
					DbTable buildDbTable = this.buildDbTable(sheet);
					if (buildDbTable != null) {
						dbTables.add(buildDbTable);
					}
				}
			}
		} catch (Exception e) {
			throw Throwables.propagate(e);
		}
	}

	private DbConfig buildDbConfig(Sheet sheet) {
		DbConfig dbConfig = new DbConfig();
		Map<String, String> key2Value = key2Value(sheet);
		BeanWrapperImpl wrapper = new BeanWrapperImpl(dbConfig);
		wrapper.setPropertyValues(key2Value);
		return dbConfig;
	}

	private Map<String, String> key2Value(Sheet sheet) {
		Map<String, String> key2Value = Maps.newHashMap();
		int rows = sheet.getRows();
		for (int i = 0; i < rows; i++) {
			String key = getContent(sheet.getCell(0, i));
			if (key != null) {
				String value = getContent(sheet.getCell(1, i));
				key2Value.put(key, value);
			}
		}
		return key2Value;
	}
	
	private DbTable buildDbTable(Sheet sheet) {
		try {
			return this.doBuildDbTable(sheet);
		} catch (Exception e) {
			return null;
		}
	}
	
	private DbTable doBuildDbTable(Sheet sheet) {
		String tableName = getContent(sheet.getCell(TABLE_NAME_COORDINATE.getValue0(), TABLE_NAME_COORDINATE.getValue1()));
		// int columns = sheet.getColumns();
		int rows = sheet.getRows();
		DbColumn pk = null;
		List<DbColumn> dbColumns = Lists.newArrayList();
		for (int i = ROW_START_INDEX; i < rows; i++) {
			String name = getContent(sheet.getCell(1, i));
			if (StringUtils.isBlank(name)) {
				break;
			}
			DbColumn dbcolumn = new DbColumn();
			dbcolumn.setName(name.toUpperCase());
			dbcolumn.setType(getContent(sheet.getCell(2, i)));
			dbcolumn.setSize(getContent(sheet.getCell(3, i)));
			dbcolumn.setPk("y".equalsIgnoreCase(getContent(sheet.getCell(4, i))));
			dbcolumn.setNullable("y".equalsIgnoreCase(ObjectUtils.firstNonNull(getContent(sheet.getCell(5, i)), "y")));
			dbcolumn.setComment(getContent(sheet.getCell(6, i)));
			//
			if (dbcolumn.isPk()) {
				pk = dbcolumn;
				continue;
			}
			//
			dbColumns.add(dbcolumn);
		}
		//
		DbTable dbTable = new DbTable();
		dbTable.setName(tableName);
		dbTable.setPk(pk);
		dbTable.setDbColumns(dbColumns);
		return dbTable;
	}
	
	private String getContent(Cell cell) {
		String contents = cell.getContents();
		if (StringUtils.isBlank(contents)) {
			return null;
		} else {
			return contents;
		}
	}

	public DbConfig getDbConfig() {
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
