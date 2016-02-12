package org.blueo.db;

import java.util.Formatter;
import java.util.List;

import org.blueo.commons.FormatterWrapper;
import org.blueo.db.java.DataLoader;
import org.blueo.db.java.PojoBuildUtils;
import org.blueo.db.sql.DdlBuildUtils;
import org.blueo.db.vo.DbTable;
import org.blueo.pojogen.JavaFileGenerator;

public class DbTool {
	private final String excelPath;
	private boolean printToConsole;
	// Internal process fields
	private DbConfig dbConfig;
	private List<DbTable> dbTables;
	
	private DbTool(String excelPath) {
		super();
		this.excelPath = excelPath;
	}

	public static DbTool build(String excelPath) {
		DbTool dbTool = new DbTool(excelPath);
		dbTool.init();
		return dbTool;
	}
	
	private void init() {
		DataLoader loader = DataLoader.build(excelPath);
		dbConfig = loader.getDbConfig();
		dbTables = loader.getDbTables();
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
			formatterWrapper.formatln(DdlBuildUtils.generateCreateSql(dbTable));
		}
		formatterWrapper.close();
	}
	
	public void generatePos() {
		for (DbTable dbTable : dbTables) {
			JavaFileGenerator javaFileGenerator;
			if (printToConsole) {
				javaFileGenerator = new JavaFileGenerator(PojoBuildUtils.buildEntityClass(dbTable, dbConfig));
			} else {
				javaFileGenerator = new JavaFileGenerator(PojoBuildUtils.buildEntityClass(dbTable, dbConfig), dbConfig.getSourceDir());
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

	public DbConfig getDbConfig() {
		return dbConfig;
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
