package org.blueo.db.vo;

import java.util.List;

public class DbTable {
	private String name;
	private DbColumn pk;
	private List<DbColumn> dbColumns;

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

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("DbTable [name=");
		builder.append(name);
		builder.append(", pk=");
		builder.append(pk);
		builder.append(", dbColumns=");
		builder.append(dbColumns);
		builder.append("]");
		return builder.toString();
	}

}
