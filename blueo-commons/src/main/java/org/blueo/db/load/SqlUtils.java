package org.blueo.db.load;

import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.blueo.db.vo.DbColumn;
import org.blueo.db.vo.DbTable;
import org.springframework.util.Assert;

import com.google.common.collect.Maps;

public class SqlUtils {
	
	private static Map<String, Class<?>> sqlTypeToJavaType = Maps.newHashMap();
	
	static {
		sqlTypeToJavaType.put("bigint", Long.class);
		sqlTypeToJavaType.put("varchar", String.class);
		sqlTypeToJavaType.put("int", Integer.class);
	}
	
	public static Class<?> getJavaType(String sqlType) {
		Assert.notNull(sqlType);
		return sqlTypeToJavaType.get(sqlType.toLowerCase());
	}
	
	private static String oneLineOfCreateSql(DbColumn dbColumn) {
		String columnName = dbColumn.getName();
		String dataType = dbColumn.getType();
		String size = getColumnTypeSize(dbColumn);
		String nullable;
		if (dbColumn.isPk()) {
			nullable = "NOT NULL";
		} else {
			nullable = dbColumn.isNullable()? "NULL":"NOT NULL";
		}
		String comment;
		if (dbColumn.getComment() == null) {
			comment = "";
		} else {
			comment = String.format(" -- %s", dbColumn.getComment());
		}
		return String.format("%s%s %s%s %s,%s%s", "\t", columnName, dataType, size, nullable, comment, System.lineSeparator());
	}
	
	private static String getColumnTypeSize(DbColumn dbColumn) {
		String size = dbColumn.getSize();
		if (StringUtils.isBlank(size)) {
			return "";
		} else {
			return String.format("(%s)", size);
		}
	}

	public static String generateCreateSql(DbTable dbTable) {
		StringBuilder sb = new StringBuilder();
		sb.append(String.format("CREATE TABLE %s (%s", dbTable.getName(), System.lineSeparator()));
		for (DbColumn dbColumn : dbTable.getDbColumns()) {
			sb.append(oneLineOfCreateSql(dbColumn));
		}
		int lastIndexOf = sb.lastIndexOf(",");
		sb.deleteCharAt(lastIndexOf);
		sb.append(String.format(");", dbTable.getName()));
		return sb.toString();
	}
}
