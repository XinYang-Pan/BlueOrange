package org.blueo.db.sql;

import java.util.Formatter;
import java.util.Iterator;

import org.apache.commons.collections4.CollectionUtils;
import org.blueo.commons.FormatterWrapper;
import org.blueo.db.vo.DbColumn;
import org.blueo.db.vo.DbTable;

public class DdlBuildUtils {

	public static String generateCreateSql(DbTable dbTable) {
		try (FormatterWrapper fw = new FormatterWrapper(new Formatter(new StringBuilder()))) {
			fw.formatln("CREATE TABLE %s (", dbTable.getName());
			// pk
			DbColumn pk = dbTable.getPk();
			if (pk != null) {
				fw.formatln(1, pk.oneLineOfCreateSql(CollectionUtils.isEmpty(dbTable.getDbColumns()), true));
			}
			// others
			Iterator<DbColumn> it = dbTable.getDbColumns().iterator();
			while (it.hasNext()) {
				DbColumn dbColumn = (DbColumn) it.next();
				fw.formatln(1, dbColumn.oneLineOfCreateSql(!it.hasNext(), true));
			}
			fw.formatln(")");
			return fw.toString();
		}
	}

}
