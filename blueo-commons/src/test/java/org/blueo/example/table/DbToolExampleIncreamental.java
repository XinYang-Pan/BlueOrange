package org.blueo.example.table;

import java.io.IOException;
import java.net.URL;

import jxl.read.biff.BiffException;

import org.blueo.commons.tostring.ToStringUtils;
import org.blueo.db.DbTool;
import org.blueo.db.sql.PostgresSqlBuilder;

public class DbToolExampleIncreamental {

	public static void main(String[] args) throws BiffException, IOException {
		URL url = DbToolExampleIncreamental.class.getResource("test1.xls");
		URL pre = DbToolExampleIncreamental.class.getResource("test0.xls");
		DbTool dbTool = DbTool.build(url.getPath(), pre.getPath());
		//
		dbTool.setSqlBuilder(new PostgresSqlBuilder());
//		dbTool.setPrintToConsole(true);
		System.out.println("********************************");
		System.out.println(ToStringUtils.wellFormat(dbTool.getDbConfig()));
		System.out.println("********************************");
		dbTool.generateCreateDdls();
		System.out.println("********************************");
		dbTool.generateEnums();
		System.out.println("********************************");
		dbTool.generatePoAndDaos();
		System.out.println("********************************");
	}

}
