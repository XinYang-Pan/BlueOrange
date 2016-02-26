package org.blueo.commons.persistent.jdbc.impl;

import java.util.Collections;
import java.util.List;

import javax.annotation.PostConstruct;

import org.blueo.commons.persistent.entity.EntityTable;
import org.blueo.commons.persistent.jdbc.BlueoJdbcs;
import org.blueo.commons.persistent.jdbc.util.ColumnPpss;
import org.springframework.jdbc.core.ParameterizedPreparedStatementSetter;

public class JdbcInsert<T, K> extends JdbcOperation<T, K> {
	//
	private String insertSql;
	private ParameterizedPreparedStatementSetter<T> insertPss;

	@PostConstruct
	public void init() {
		insertSql = BlueoJdbcs.buildInsertSql(entityTable.getTableName(), EntityTable.getColumnNames(entityTable.getNoneGenValueCols()));
		insertPss = new ColumnPpss<T>(entityTable.getNoneGenValueCols());
	}

	public K insert(T t) {
		if (!entityTable.getIdCol().isGeneratedValue()) {
			this.insert(Collections.singletonList(t));
			return getIdHandler().getId(t);
		}
		this.insert(Collections.singletonList(t));
		return null;
	}

	public void insert(List<T> entities) {
		jdbcTemplate.batchUpdate(insertSql, entities, this.batchSize(), insertPss);
	}

}
