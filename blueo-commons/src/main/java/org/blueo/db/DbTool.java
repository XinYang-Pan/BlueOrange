package org.blueo.db;

import java.util.Formatter;
import java.util.List;

import org.blueo.commons.FormatterWrapper;
import org.blueo.db.config.DbGlobalConfig;
import org.blueo.db.java.DataLoader;
import org.blueo.db.java.PojoBuildUtils;
import org.blueo.db.sql.DdlBuildUtils;
import org.blueo.db.vo.DbEnum;
import org.blueo.db.vo.DbTable;
import org.blueo.pojogen.EnumGenerator;
import org.blueo.pojogen.JavaFileGenerator;
import org.blueo.pojogen.bo.PojoClass;

public class DbTool {
	private final String excelPath;
	private boolean printToConsole;
	// Internal process fields
	private DbGlobalConfig dbConfig;
	private List<DbTable> dbTables;
	private List<DbEnum> dbEnums;
	
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
		dbEnums = loader.getDbEnums();
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
	
	public void generateEnums() {
		for (DbEnum dbEnum : dbEnums) {
			PojoClass pojoClass = new PojoClass();
			pojoClass.setPackageName(dbConfig.getEnumPackage());
			pojoClass.setName(dbEnum.getName());
			// 
			EnumGenerator enumGenerator;
			if (printToConsole) {
				enumGenerator = new EnumGenerator(pojoClass);
			} else {
				enumGenerator = new EnumGenerator(pojoClass, dbConfig.getSourceDir());
			}
			enumGenerator.setValues(dbEnum.getValues());
			enumGenerator.generateClassCode();
		}
	}
	
	public void generatePoAndDaos() {
		for (DbTable dbTable : dbTables) {
			PojoClass pojoClass = PojoBuildUtils.buildEntityClass(dbTable, dbConfig);
			PojoClass daoClass = PojoBuildUtils.buildDaoClass(pojoClass, dbConfig);
			if (printToConsole) {
				new JavaFileGenerator(pojoClass).generateClassCode();
				new JavaFileGenerator(daoClass).generateClassCode();
			} else {
				new JavaFileGenerator(pojoClass, dbConfig.getSourceDir()).generateClassCode();
				new JavaFileGenerator(daoClass, dbConfig.getSourceDir()).generateClassCode();
			}
		}
	}
	
	// -----------------------------
	// ----- Get Set ToString HashCode Equals
	// -----------------------------
	
	public String getExcelPath() {
		return excelPath;
	}

	public DbGlobalConfig getDbConfig() {
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
