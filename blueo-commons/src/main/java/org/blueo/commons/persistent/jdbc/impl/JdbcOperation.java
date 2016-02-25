package org.blueo.commons.persistent.jdbc.impl;

import org.blueo.commons.persistent.core.dao.po.id.HasId;
import org.blueo.commons.persistent.jdbc.util.BoTable;
import org.springframework.jdbc.core.JdbcTemplate;

public class JdbcOperation<T extends HasId<K>, K> {
	protected static final int BATCH_SIZE = 1000;
	// DI
	protected JdbcTemplate jdbcTemplate;
	protected BoTable<T> boTable;

	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}

	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	public BoTable<T> getBoTable() {
		return boTable;
	}

	public void setBoTable(BoTable<T> boTable) {
		this.boTable = boTable;
	}

}
