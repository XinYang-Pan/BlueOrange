package org.blueo.db.sql;

import org.blueo.db.vo.DbColumn;

public class PostgresSqlBuilder extends GenericSqlBuilder {

	@Override
	protected String alterModify(String tableName, DbColumn dbColumn) {
		String alterType = String.format("ALTER TABLE %s ALTER %s TYPE %s;", tableName, dbColumn.getName(), getTypeWithLengthStr(dbColumn));
		String alterNullable = String.format("ALTER TABLE %s ALTER %s %s NOT NULL;", tableName, dbColumn.getName(), dbColumn.isNullableInBool() ? "DROP" : "SET");
		return String.format("%s%n%s", alterType, alterNullable);
	}

}
