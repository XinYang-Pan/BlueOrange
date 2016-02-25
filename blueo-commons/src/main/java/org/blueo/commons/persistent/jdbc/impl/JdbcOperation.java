package org.blueo.commons.persistent.jdbc.impl;

import org.blueo.commons.persistent.core.dao.po.id.IdWrapper;
import org.blueo.commons.persistent.entity.BoTable;
import org.springframework.jdbc.core.JdbcTemplate;

public class JdbcOperation<T, K> {
	protected static final int BATCH_SIZE = 1000;
	// DI
	protected JdbcTemplate jdbcTemplate;
	protected BoTable<T> boTable;
	protected IdWrapper<T, K> idWrapper;

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

	public IdWrapper<T, K> getIdWrapper() {
		return idWrapper;
	}

	public void setIdWrapper(IdWrapper<T, K> idWrapper) {
		this.idWrapper = idWrapper;
	}

}
