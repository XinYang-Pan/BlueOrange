package org.blueo.db.config;

import java.util.List;

import com.google.common.base.Splitter;
import com.google.common.collect.Lists;

public class DbGlobalConfig {
	// Java
	private String sourceDir = "./tmp/src";
	private String poPackage = "org.blueo.db.po";
	private String poSuperclass;
	private String poInterfaces = "java.io.Serializable";
	private String daoPackage;
	private String daoSuperclass;
	private String daoInterfaces;
	private String enumPackage;
	// DDL
	private String ddlDir = "./tmp/ddl";
	private String ddlFileName = "createTables.sql";
	// table default
	private DbTableConfig dbTableConfig;
	
	// -----------------------------
	// ----- Non-Static Methods
	// -----------------------------
	
	public List<String> getPoInterfacesInList() {
		if (poInterfaces == null) {
			return Lists.newArrayList();
		}
		Splitter splitter = Splitter.on(',').trimResults().omitEmptyStrings();
		return splitter.splitToList(poInterfaces);
	}

	public List<String> getDaoInterfacesInList() {
		if (daoInterfaces == null) {
			return Lists.newArrayList();
		}
		Splitter splitter = Splitter.on(',').trimResults().omitEmptyStrings();
		return splitter.splitToList(daoInterfaces);
	}

	// -----------------------------
	// ----- Get Set ToString HashCode Equals
	// -----------------------------
	
	public String getPoPackage() {
		return poPackage;
	}

	public void setPoPackage(String poPackage) {
		this.poPackage = poPackage;
	}

	public String getPoSuperclass() {
		return poSuperclass;
	}

	public void setPoSuperclass(String poSuperclass) {
		this.poSuperclass = poSuperclass;
	}

	public String getPoInterfaces() {
		return poInterfaces;
	}

	public void setPoInterfaces(String poInterfaces) {
		this.poInterfaces = poInterfaces;
	}

	public String getDaoPackage() {
		return daoPackage;
	}

	public void setDaoPackage(String daoPackage) {
		this.daoPackage = daoPackage;
	}

	public String getDaoSuperclass() {
		return daoSuperclass;
	}

	public void setDaoSuperclass(String daoSuperclass) {
		this.daoSuperclass = daoSuperclass;
	}

	public String getDaoInterfaces() {
		return daoInterfaces;
	}

	public void setDaoInterfaces(String daoInterfaces) {
		this.daoInterfaces = daoInterfaces;
	}

	public String getSourceDir() {
		return sourceDir;
	}

	public void setSourceDir(String sourceDir) {
		this.sourceDir = sourceDir;
	}

	public String getDdlDir() {
		return ddlDir;
	}

	public void setDdlDir(String ddlDir) {
		this.ddlDir = ddlDir;
	}

	public String getDdlFileName() {
		return ddlFileName;
	}

	public void setDdlFileName(String ddlFileName) {
		this.ddlFileName = ddlFileName;
	}

	public DbTableConfig getDbTableConfig() {
		return dbTableConfig;
	}

	public void setDbTableConfig(DbTableConfig dbTableConfig) {
		this.dbTableConfig = dbTableConfig;
	}

	public String getEnumPackage() {
		return enumPackage;
	}

	public void setEnumPackage(String enumPackage) {
		this.enumPackage = enumPackage;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("DbGlobalConfig [sourceDir=");
		builder.append(sourceDir);
		builder.append(", poPackage=");
		builder.append(poPackage);
		builder.append(", poSuperclass=");
		builder.append(poSuperclass);
		builder.append(", poInterfaces=");
		builder.append(poInterfaces);
		builder.append(", daoPackage=");
		builder.append(daoPackage);
		builder.append(", daoSuperclass=");
		builder.append(daoSuperclass);
		builder.append(", daoInterfaces=");
		builder.append(daoInterfaces);
		builder.append(", enumPackage=");
		builder.append(enumPackage);
		builder.append(", ddlDir=");
		builder.append(ddlDir);
		builder.append(", ddlFileName=");
		builder.append(ddlFileName);
		builder.append(", dbTableConfig=");
		builder.append(dbTableConfig);
		builder.append("]");
		return builder.toString();
	}

}
