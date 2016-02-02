package org.blueo.db.load;

import java.io.File;
import java.util.List;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.blueo.db.vo.DbColumn;
import org.blueo.db.vo.DbTable;
import org.javatuples.Pair;

import com.google.common.collect.Lists;

public class Loader {
	private static Pair<Integer, Integer> TABLE_NAME_COORDINATE = Pair.with(1, 1);
	private static final int ROW_START_INDEX = 3;
	
	private static String getContent(Cell cell) {
		String contents = cell.getContents();
		if (StringUtils.isBlank(contents)) {
			return null;
		} else {
			return contents;
		}
	}
	
	// for each sheet
	public static List<DbTable> loadFromExcel(String path) {
		try {
			Workbook workbook = Workbook.getWorkbook(new File(path));
			Sheet sheet = workbook.getSheet(0);
			String tableName = getContent(sheet.getCell(TABLE_NAME_COORDINATE.getValue0(), TABLE_NAME_COORDINATE.getValue1()));
//			int columns = sheet.getColumns();
			int rows = sheet.getRows();
			DbColumn pk = null;
			List<DbColumn> dbColumns = Lists.newArrayList();
			for (int i = ROW_START_INDEX; i < rows; i++) {
				String name = getContent(sheet.getCell(1, i));
				if (StringUtils.isBlank(name)) {
					break;
				}
				DbColumn dbcolumn = new DbColumn();
				dbcolumn.setName(name);
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
			return Lists.newArrayList(dbTable);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
