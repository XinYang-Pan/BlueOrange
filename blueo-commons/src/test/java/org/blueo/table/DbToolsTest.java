package org.blueo.table;

import java.io.IOException;
import java.util.List;

import jxl.read.biff.BiffException;

import org.blueo.commons.tostring.ToStringUtils;
import org.blueo.db.load.Loader;
import org.blueo.db.load.SqlUtils;
import org.blueo.db.vo.DbTable;

public class DbToolsTest {
	
	public static void main(String[] args) throws BiffException, IOException {
		List<DbTable> dbTables = Loader.loadFromExcel("src/test/java/org/blueo/table/test.xls");
		System.out.println(ToStringUtils.wellFormat(dbTables));
		for (DbTable dbTable : dbTables) {
			System.out.println(SqlUtils.generateCreateSql(dbTable));
		}
	}
}
