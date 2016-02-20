package org.blueo.db.sql;

import org.blueo.db.vo.DbTablePair;

public interface SqlBuilder {
	
	public String createOrAlterSql(DbTablePair dbTablePair);
	
}
