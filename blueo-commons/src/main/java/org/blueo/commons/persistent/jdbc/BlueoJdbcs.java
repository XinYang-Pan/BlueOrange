package org.blueo.commons.persistent.jdbc;

import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.google.common.collect.Lists;

public class BlueoJdbcs {
	private static final String SEPARATOR = ", ";

	public static String buildInsertSql(String tableName, List<String> columnNames) {
		String columnPart = StringUtils.join(columnNames, SEPARATOR);
		String valuePart = StringUtils.repeat("?", SEPARATOR, columnNames.size());
		return String.format("INSERT INTO %s(%s) VALUES(%s)", tableName, columnPart, valuePart);
	}

	public static String buildUpdateSql(String tableName, List<String> columnNames, String idColumnName) {
		List<String> setPiece = Lists.newArrayList();
		for (String columnName : columnNames) {
			setPiece.add(String.format("%s=?", columnName));
		}
		String setPart = StringUtils.join(setPiece, SEPARATOR);
		return String.format("UPDATE %s SET %s WHERE %s=?", tableName, setPart, idColumnName);
	}

	public static String buildDeleteSql(String tableName, String idColumnName) {
		return String.format("DELETE FROM %s WHERE %s=?", tableName, idColumnName);
	}

}
