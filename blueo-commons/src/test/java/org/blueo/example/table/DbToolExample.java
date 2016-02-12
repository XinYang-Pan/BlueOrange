package org.blueo.example.table;

import java.io.IOException;
import java.net.URL;

import jxl.read.biff.BiffException;

import org.blueo.db.DbConfig;
import org.blueo.db.DbTool;

public class DbToolExample {

	public static void main(String[] args) throws BiffException, IOException {
		URL url = DbToolExample.class.getResource("test.xls");
		DbConfig dbConfig = new DbConfig();
		DbTool dbTool = DbTool.buildAndInit(url.getPath(), dbConfig);
		//
		dbTool.setPrintToConsole(true);
		System.out.println("********************************");
		dbTool.generateCreateDdls();
		System.out.println("********************************");
		dbTool.generatePos();
		System.out.println("********************************");
	}

}
