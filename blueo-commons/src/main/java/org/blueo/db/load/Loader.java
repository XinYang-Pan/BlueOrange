package org.blueo.db.load;

import java.io.File;
import java.util.List;

import jxl.Sheet;
import jxl.Workbook;

import org.apache.commons.lang3.StringUtils;
import org.blueo.db.vo.DbColumn;
import org.blueo.db.vo.DbTable;

import com.google.common.collect.Lists;

public class Loader {
	
	// for each sheet
	public static List<DbTable> loadFromExcel(String path) {
		try {
			Workbook workbook = Workbook.getWorkbook(new File(path));
			Sheet sheet = workbook.getSheet(0);
			String tableName = sheet.getCell(1, 1).getContents();
//			int columns = sheet.getColumns();
			int rows = sheet.getRows();
			DbColumn pk = null;
			List<DbColumn> dbColumns = Lists.newArrayList();
			for (int i = 3; i < rows; i++) {
				String name = sheet.getCell(1, i).getContents();
				if (StringUtils.isBlank(name)) {
					break;
				}
				DbColumn dbcolumn = new DbColumn();
				dbcolumn.setName(name);
				dbcolumn.setType(sheet.getCell(2, i).getContents());
				dbcolumn.setSize(sheet.getCell(3, i).getContents());
				dbcolumn.setPk("y".equalsIgnoreCase(sheet.getCell(4, i).getContents()));
				dbcolumn.setNullable("y".equalsIgnoreCase(sheet.getCell(5, i).getContents()));
				dbcolumn.setComments(sheet.getCell(6, i).getContents());
				// 
				if (dbcolumn.isPk()) {
					pk = dbcolumn;
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
