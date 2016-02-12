package org.blueo.db;

import java.util.List;

import com.google.common.collect.Lists;

public class DbConfig {
	// Java 
	private String sourceDir = "./tmp/src";
	private String poPackage = "org.blueo.db.po";
	private String poSuperclass;
	private List<String> poInterfaces = Lists.newArrayList("java.io.Serializable");
	private String daoPackage;
	private String daoSuperclass;
	private List<String> daoInterfaces;
	// DDL 
	private String ddlDir = "./tmp/ddl";
	private String ddlFileName = "createTables.sql";

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

	public List<String> getPoInterfaces() {
		return poInterfaces;
	}

	public void setPoInterfaces(List<String> poInterfaces) {
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

	public List<String> getDaoInterfaces() {
		return daoInterfaces;
	}

	public void setDaoInterfaces(List<String> daoInterfaces) {
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

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("DbConfig [sourceDir=");
		builder.append(sourceDir);
		builder.append(", ddlDir=");
		builder.append(ddlDir);
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
		builder.append("]");
		return builder.toString();
	}

	public String getDdlFileName() {
		return ddlFileName;
	}

	public void setDdlFileName(String ddlFileName) {
		this.ddlFileName = ddlFileName;
	}

}
