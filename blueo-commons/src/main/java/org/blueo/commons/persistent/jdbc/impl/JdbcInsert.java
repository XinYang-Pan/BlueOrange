package org.blueo.commons.persistent.jdbc.impl;

import java.util.Collections;
import java.util.List;

import javax.annotation.PostConstruct;

import org.blueo.commons.persistent.core.dao.po.HasId;
import org.blueo.commons.persistent.jdbc.BlueoJdbcs;
import org.blueo.commons.persistent.jdbc.util.BoTable;
import org.blueo.commons.persistent.jdbc.util.ColumnPpss;
import org.springframework.jdbc.core.ParameterizedPreparedStatementSetter;

public class JdbcInsert<T extends HasId<K>, K> extends JdbcOperation<T, K> {
	//
	private String insertSql;
	private ParameterizedPreparedStatementSetter<T> insertPss;

	@PostConstruct
	public void init() {
		insertSql = BlueoJdbcs.buildInsertSql(boTable.getTableName(), BoTable.getColumnNames(boTable.getNoneGenValueCols()));
		insertPss = new ColumnPpss<T>(boTable.getNoneGenValueCols());
	}

	public K insert(T t) {
		if (!boTable.getIdCol().isGeneratedValue()) {
			this.insert(Collections.singletonList(t));
			return t.getId();
		}
		this.insert(Collections.singletonList(t));
		return null;
	}

	public void insert(List<T> entities) {
		jdbcTemplate.batchUpdate(insertSql, entities, BATCH_SIZE, insertPss);
	}

}
