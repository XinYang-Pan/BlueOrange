package org.blueo.db.sql;

import java.util.Formatter;
import java.util.Iterator;

import org.apache.commons.collections4.CollectionUtils;
import org.blueo.commons.FormatterWrapper;
import org.blueo.db.vo.DbColumn;
import org.blueo.db.vo.DbTable;
import org.blueo.db.vo.DbTablePair;

public class GenericSqlBuilder implements SqlBuilder {

	protected String alterAdd(String tableName, DbColumn dbColumn) {
		return String.format("ALTER TABLE %s ADD %s;", tableName, getDefinitionSql(dbColumn));
	}

	protected String alterDrop(String tableName, DbColumn dbColumn) {
		return String.format("ALTER TABLE %s DROP COLUMN %s;", tableName, dbColumn.getName());
	}

	protected String alterModify(String tableName, DbColumn dbColumn) {
		return String.format("ALTER TABLE %s MODIFY %s;", tableName, getDefinitionSql(dbColumn));
	}
	
	public String getDefinitionSql(DbColumn dbColumn) {
		String pkStr = "";
		if (dbColumn.isPkInBool()) {
			pkStr = " PRIMARY KEY";
		}
		return String.format("%s %s %s%s", dbColumn.getName(), dbColumn.getFullTypeStr(), getNullableStr(dbColumn), pkStr);
	}

	protected String getNullableStr(DbColumn dbColumn) {
		String nullable;
		if (dbColumn.isPkInBool()) {
			nullable = "NOT NULL";
		} else {
			nullable = dbColumn.isNullableInBool()? "NULL":"NOT NULL";
		}
		return nullable;
	}
	
	protected String oneLineOfCreateSql(DbColumn dbColumn, boolean lastLine, boolean withComments) {
		String lastLineStr = "";
		if (!lastLine) {
			lastLineStr = ",";
		}
		String comment = "";
		if (withComments && dbColumn.getComment() != null) {
			comment = String.format(" -- %s", dbColumn.getComment());
		} 
		return String.format("%s%s%s", getDefinitionSql(dbColumn), lastLineStr, comment);
	}
	
	protected String createSql(DbTable dbTable) {
		try (FormatterWrapper fw = new FormatterWrapper(new Formatter(new StringBuilder()))) {
			fw.formatln("CREATE TABLE %s (", dbTable.getName());
			// pk
			DbColumn pk = dbTable.getPk();
			if (pk != null) {
				fw.formatln(1, oneLineOfCreateSql(pk, CollectionUtils.isEmpty(dbTable.getDbColumns()), true));
			}
			// others
			Iterator<DbColumn> it = dbTable.getDbColumns().iterator();
			while (it.hasNext()) {
				DbColumn dbColumn = (DbColumn) it.next();
				fw.formatln(1, oneLineOfCreateSql(dbColumn, !it.hasNext(), true));
			}
			fw.formatln(");");
			return fw.toString();
		}
	}

	public String createOrAlterSql(DbTablePair dbTablePair) {
		try (FormatterWrapper fw = new FormatterWrapper(new Formatter(new StringBuilder()))) {
			DbTable current = dbTablePair.getCurrent();
			fw.formatln("-- %s", current.getName());
			// CREATE
			if (dbTablePair.getPrevious() == null) {
				fw.format(createSql(current));
				return fw.toString();
			}
			// ALTER
			for (DbColumn dbColumn : dbTablePair.getAdd()) {
				fw.formatln(alterAdd(current.getName(), dbColumn));
			}
			for (DbColumn dbColumn : dbTablePair.getModify()) {
				fw.formatln(alterModify(current.getName(), dbColumn));
			}
			for (DbColumn dbColumn : dbTablePair.getDrop()) {
				fw.formatln(alterDrop(current.getName(), dbColumn));
			}
			return fw.toString();
		}
	}
	
}
