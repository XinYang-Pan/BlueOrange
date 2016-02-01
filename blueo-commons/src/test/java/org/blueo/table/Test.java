package org.blueo.table;

import java.io.IOException;
import java.util.List;

import jxl.read.biff.BiffException;

import org.blueo.commons.tostring.IterableMultilineToString;
import org.blueo.db.load.Loader;
import org.blueo.db.load.SqlUtils;
import org.blueo.db.vo.DbTable;

public class Test {
	
	public static void main(String[] args) throws BiffException, IOException {
		List<DbTable> dbTables = Loader.loadFromExcel("src/test/java/org/blueo/table/test.xls");
		for (DbTable dbTable : dbTables) {
			System.out.println(SqlUtils.generateCreateSql(dbTable));
		}
		System.out.println(IterableMultilineToString.toString(dbTables));
		System.out.println(Number.class.isAssignableFrom(Integer.class));
	}
}
