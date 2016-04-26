package org.blueo.commons.persistent.jdbc.impl;

import javax.annotation.PostConstruct;

import org.apache.commons.lang3.ObjectUtils;
import org.blueo.commons.persistent.dao.po.id.IdHandler;
import org.blueo.commons.persistent.entity.EntityTable;
import org.springframework.jdbc.core.JdbcTemplate;

public class JdbcOperation<T, K> {
	private static final int DEFAULT_BATCH_SIZE = 5000;
	// DI
	protected JdbcTemplate jdbcTemplate;
	protected EntityTable<T> entityTable;
	private IdHandler<T, K> idHandler;
	private Integer batchSize;
	
	@PostConstruct
	public void init() {
		
	}
	
	protected int batchSize() {
		return ObjectUtils.defaultIfNull(batchSize, DEFAULT_BATCH_SIZE);
	}
	
	// -----------------------------
	// ----- Get Set ToString HashCode Equals
	// -----------------------------
	
	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}

	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	public EntityTable<T> getBoTable() {
		return entityTable;
	}

	public void setBoTable(EntityTable<T> boTable) {
		this.entityTable = boTable;
	}

	public IdHandler<T, K> getIdHandler() {
		return idHandler;
	}

	public void setIdHandler(IdHandler<T, K> idHandler) {
		this.idHandler = idHandler;
	}

	public Integer getDefaultBatchSize() {
		return batchSize;
	}

	public void setBatchSize(Integer batchSize) {
		this.batchSize = batchSize;
	}

}
