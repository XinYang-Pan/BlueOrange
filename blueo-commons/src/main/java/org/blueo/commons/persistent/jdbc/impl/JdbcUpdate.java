package org.blueo.commons.persistent.jdbc.impl;

import java.util.Collections;
import java.util.List;

import javax.annotation.PostConstruct;

import org.blueo.commons.persistent.entity.EntityTable;
import org.blueo.commons.persistent.jdbc.BlueoJdbcs;
import org.blueo.commons.persistent.jdbc.util.ColumnPpss;
import org.springframework.jdbc.core.ParameterizedPreparedStatementSetter;

public class JdbcUpdate<T, K> extends JdbcOperation<T, K> {
	//
	private String updateSql;
	private ParameterizedPreparedStatementSetter<T> updatePss;

	@PostConstruct
	public void init() {
		// update
		updateSql = BlueoJdbcs.buildUpdateSql(entityTable.getTableName(), EntityTable.getColumnNames(entityTable.getNoneIdCols()), entityTable.getIdCol().getColumnName());
		updatePss = new ColumnPpss<T>(entityTable.getAllCols());
	}

	public void update(T t) {
		this.update(Collections.singletonList(t));
	}

	public void update(List<T> entities) {
		jdbcTemplate.batchUpdate(updateSql, entities, this.batchSize(), updatePss);
	}

}
