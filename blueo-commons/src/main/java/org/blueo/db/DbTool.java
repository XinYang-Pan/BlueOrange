package org.blueo.db;

import java.util.Formatter;
import java.util.List;

import org.blueo.commons.FormatterWrapper;
import org.blueo.db.util.DbUtils;
import org.blueo.db.util.DdlUtils;
import org.blueo.db.util.Loader;
import org.blueo.db.vo.DbTable;
import org.blueo.pojogen.JavaFileGenerator;

public class DbTool {
	private String excelPath;
	private DbConfig dbConfig;
	private boolean printToConsole;
	// Internal process fields
	private List<DbTable> dbTables;
	
	public static DbTool buildAndInit(String excelPath, DbConfig dbConfig) {
		DbTool dbTool = new DbTool();
		dbTool.setExcelPath(excelPath);
		dbTool.setDbConfig(dbConfig);
		dbTool.init();
		return dbTool;
	}
	
	public void init() {
		dbTables = Loader.loadFromExcel(excelPath);
	}

	public void generateCreateDdls() {
		FormatterWrapper formatterWrapper;
		if (printToConsole) {
			formatterWrapper = new FormatterWrapper(new Formatter(System.out));
		} else {
			String filePath = String.format("%s/%s", dbConfig.getDdlDir(), dbConfig.getDdlFileName());
			formatterWrapper = new FormatterWrapper(FormatterWrapper.createFormatter(filePath));
		}
		for (DbTable dbTable : dbTables) {
			formatterWrapper.formatln(DdlUtils.generateCreateSql(dbTable));
		}
		formatterWrapper.close();
	}
	
	public void generatePos() {
		for (DbTable dbTable : dbTables) {
			JavaFileGenerator javaFileGenerator;
			if (printToConsole) {
				javaFileGenerator = new JavaFileGenerator(DbUtils.buildEntityClass(dbTable));
			} else {
				javaFileGenerator = new JavaFileGenerator(DbUtils.buildEntityClass(dbTable), dbConfig.getSourceDir());
			}
			javaFileGenerator.generateClassCode();
		}
	}
	
	// -----------------------------
	// ----- Get Set ToString HashCode Equals
	// -----------------------------
	public String getExcelPath() {
		return excelPath;
	}

	public void setExcelPath(String excelPath) {
		this.excelPath = excelPath;
	}

	public DbConfig getDbConfig() {
		return dbConfig;
	}

	public void setDbConfig(DbConfig dbConfig) {
		this.dbConfig = dbConfig;
	}

	public boolean isPrintToConsole() {
		return printToConsole;
	}

	public void setPrintToConsole(boolean printToConsole) {
		this.printToConsole = printToConsole;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("DbTool [excelPath=");
		builder.append(excelPath);
		builder.append(", dbConfig=");
		builder.append(dbConfig);
		builder.append(", printToConsole=");
		builder.append(printToConsole);
		builder.append(", dbTables=");
		builder.append(dbTables);
		builder.append("]");
		return builder.toString();
	}

}
