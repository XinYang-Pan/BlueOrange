package org.blueo.table;

import java.io.IOException;
import java.util.List;

import jxl.read.biff.BiffException;

import org.apache.commons.lang3.StringUtils;
import org.blueo.db.load.Loader;
import org.blueo.db.vo.DbColumn;
import org.blueo.db.vo.DbTable;

public class Test {
	
	public static String oneLine(DbColumn dbColumn) {
		String columnName = dbColumn.getName();
		String dataType = dbColumn.getType();
		String size = getSize(dbColumn);
		return String.format("%s%s %s%s,%s", "\t", columnName, dataType, size, System.lineSeparator());
	}
	
	private static String getSize(DbColumn dbColumn) {
		String size = dbColumn.getSize();
		if (StringUtils.isBlank(size)) {
			return "";
		} else {
			return String.format("(%s)", size);
		}
	}

	public static String string(DbTable dbTable) {
		StringBuilder sb = new StringBuilder();
		sb.append(String.format("CREATE TABLE %s (%s", dbTable.getName(), System.lineSeparator()));
		for (DbColumn dbColumn : dbTable.getDbColumns()) {
			sb.append(oneLine(dbColumn));
		}
		int lastIndexOf = sb.lastIndexOf(",");
		sb.deleteCharAt(lastIndexOf);
		sb.append(String.format(");", dbTable.getName()));
		return sb.toString();
	}
	
	public static void main(String[] args) throws BiffException, IOException {
		List<DbTable> dbTables = Loader.loadFromExcel("src/test/java/org/blueo/table/test.xls");
		for (DbTable dbTable : dbTables) {
			System.out.println(string(dbTable));
		}
		System.out.println(dbTables);
	}
}
