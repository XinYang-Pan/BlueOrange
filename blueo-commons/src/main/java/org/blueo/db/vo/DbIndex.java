package org.blueo.db.vo;

import java.util.List;

public class DbIndex {

	private String indexName;
	private String tableName;
	private boolean unique;
	private List<String> columnNames;

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("DbIndex [indexName=");
		builder.append(indexName);
		builder.append(", tableName=");
		builder.append(tableName);
		builder.append(", unique=");
		builder.append(unique);
		builder.append(", columnNames=");
		builder.append(columnNames);
		builder.append("]");
		return builder.toString();
	}

	public String getIndexName() {
		return indexName;
	}

	public void setIndexName(String indexName) {
		this.indexName = indexName;
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public boolean isUnique() {
		return unique;
	}

	public void setUnique(boolean unique) {
		this.unique = unique;
	}

	public List<String> getColumnNames() {
		return columnNames;
	}

	public void setColumnNames(List<String> columnNames) {
		this.columnNames = columnNames;
	}

}
