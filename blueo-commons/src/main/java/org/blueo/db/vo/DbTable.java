package org.blueo.db.vo;

import java.util.List;

import org.blueo.db.config.DbTableConfig;

public class DbTable {
	private String name;
	private String seq;
	private DbColumn pk;
	private List<DbColumn> dbColumns;
	private List<DbIndex> dbIndexs;
	private DbTableConfig dbTableConfig;

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("DbTable [name=");
		builder.append(name);
		builder.append(", seq=");
		builder.append(seq);
		builder.append(", pk=");
		builder.append(pk);
		builder.append(", dbColumns=");
		builder.append(dbColumns);
		builder.append(", dbIndexs=");
		builder.append(dbIndexs);
		builder.append(", dbTableConfig=");
		builder.append(dbTableConfig);
		builder.append("]");
		return builder.toString();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public DbColumn getPk() {
		return pk;
	}

	public void setPk(DbColumn pk) {
		this.pk = pk;
	}

	public List<DbColumn> getDbColumns() {
		return dbColumns;
	}

	public void setDbColumns(List<DbColumn> dbColumns) {
		this.dbColumns = dbColumns;
	}

	public DbTableConfig getDbTableConfig() {
		return dbTableConfig;
	}

	public void setDbTableConfig(DbTableConfig dbTableConfig) {
		this.dbTableConfig = dbTableConfig;
	}

	public String getSeq() {
		return seq;
	}

	public void setSeq(String seq) {
		this.seq = seq;
	}

	public List<DbIndex> getDbIndexs() {
		return dbIndexs;
	}

	public void setDbIndexs(List<DbIndex> dbIndexs) {
		this.dbIndexs = dbIndexs;
	}

}
