package org.blueo.db;

import java.util.Collections;
import java.util.Formatter;
import java.util.List;

import org.blueo.commons.FormatterWrapper;
import org.blueo.db.config.raw.DbGlobalConfigRawData;
import org.blueo.db.java.DataLoader;
import org.blueo.db.java.PojoBuildUtils;
import org.blueo.db.sql.GenericSqlBuilder;
import org.blueo.db.sql.SqlBuilder;
import org.blueo.db.vo.DbEnum;
import org.blueo.db.vo.DbTable;
import org.blueo.db.vo.DbTablePair;
import org.blueo.pojogen.EnumGenerator;
import org.blueo.pojogen.JavaFileGenerator;
import org.blueo.pojogen.bo.PojoClass;

import com.google.common.base.Objects;
import com.google.common.collect.Lists;

public class DbTool {
	private final String excelPath;
	private String previousExcelPath;
	private boolean printToConsole;
	private SqlBuilder sqlBuilder = new GenericSqlBuilder();
	// Internal process fields
	private DbGlobalConfigRawData dbConfig;
	private List<DbTablePair> dbTablePairs;
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

	public static DbTool build(String excelPath, String previousExcelPath) {
		DbTool dbTool = new DbTool(excelPath);
		dbTool.previousExcelPath = previousExcelPath;
		dbTool.init();
		return dbTool;
	}
	
	private void init() {
		DataLoader loader = DataLoader.build(excelPath);
		DataLoader previousLoader = null;
		if (previousExcelPath != null) {
			previousLoader = DataLoader.build(previousExcelPath);
		}
		dbConfig = loader.getDbConfig();
		dbTablePairs = this.buildDbTablePair(loader, previousLoader);
		dbEnums = loader.getDbEnums();
	}

	private List<DbTablePair> buildDbTablePair(DataLoader loader, DataLoader previousLoader) {
		List<DbTable> curr = loader.getDbTables();
		List<DbTable> prev = Collections.emptyList();
		if (previousLoader != null) {
			prev = previousLoader.getDbTables();
		}
		List<DbTablePair> dbTablePairs = Lists.newArrayList();
		// 
		for (DbTable c : curr) {
			DbTablePair dbTablePair = new DbTablePair();
			dbTablePair.setCurrent(c);
			dbTablePairs.add(dbTablePair);
			for (DbTable p : prev) {
				if (Objects.equal(c.getName(), p.getName())) {
					dbTablePair.setPrevious(p);
					break;
				}
			}
		}
		return dbTablePairs;
	}

	public void generateCreateDdls() {
		FormatterWrapper formatterWrapper;
		if (printToConsole) {
			formatterWrapper = new FormatterWrapper(new Formatter(System.out));
		} else {
			String filePath = String.format("%s/%s", dbConfig.getDdlDir(), dbConfig.getDdlFileName());
			formatterWrapper = new FormatterWrapper(FormatterWrapper.createFormatter(filePath));
		}
		for (DbTablePair dbTablePair : dbTablePairs) {
			formatterWrapper.format(sqlBuilder.createOrAlterSql(dbTablePair));
		}
		formatterWrapper.close();
	}
	
	public void generateEnums() {
		for (DbEnum dbEnum : dbEnums) {
			PojoClass pojoClass = new PojoClass();
			pojoClass.setPackageName(dbEnum.getPackageName());
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
		for (DbTablePair dbTablePair : dbTablePairs) {
			PojoClass pojoClass = PojoBuildUtils.buildEntityClass(dbTablePair.getCurrent(), dbConfig);
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

	public DbGlobalConfigRawData getDbConfig() {
		return dbConfig;
	}

	public boolean isPrintToConsole() {
		return printToConsole;
	}

	public SqlBuilder getSqlBuilder() {
		return sqlBuilder;
	}

	public void setSqlBuilder(SqlBuilder sqlBuilder) {
		this.sqlBuilder = sqlBuilder;
	}

	public void setPrintToConsole(boolean printToConsole) {
		this.printToConsole = printToConsole;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("DbTool [excelPath=");
		builder.append(excelPath);
		builder.append(", previousExcelPath=");
		builder.append(previousExcelPath);
		builder.append(", printToConsole=");
		builder.append(printToConsole);
		builder.append(", sqlBuilder=");
		builder.append(sqlBuilder);
		builder.append(", dbConfig=");
		builder.append(dbConfig);
		builder.append(", dbTablePairs=");
		builder.append(dbTablePairs);
		builder.append(", dbEnums=");
		builder.append(dbEnums);
		builder.append("]");
		return builder.toString();
	}

}
