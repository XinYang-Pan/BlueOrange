package org.blueo.db.load;

import org.apache.commons.lang3.StringUtils;
import org.blueo.db.vo.DbColumn;
import org.blueo.db.vo.DbTable;

public class SqlUtils {
	
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
		return String.format("%s%s %s%s %s,%s", "\t", columnName, dataType, size, nullable, System.lineSeparator());
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
