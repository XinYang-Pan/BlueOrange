package org.blueo.commons.persistent.jdbc.impl;

import java.util.Arrays;
import java.util.List;

import javax.annotation.PostConstruct;

import org.blueo.commons.BlueoUtils;
import org.blueo.commons.persistent.core.dao.AbstractDao;
import org.blueo.commons.persistent.core.dao.po.HasId;
import org.blueo.commons.persistent.jdbc.util.BoTable;
import org.springframework.jdbc.core.JdbcTemplate;

import com.google.common.reflect.TypeToken;

public class JdbcDao<T extends HasId<K>, K> extends AbstractDao<T, K> {
	// DI
	private JdbcTemplate jdbcTemplate;
	// 
	@SuppressWarnings("serial")
	private final Class<T> parameterizedClass = BlueoUtils.getParameterizedClass(new TypeToken<T>(this.getClass()) {});
	private BoTable<T> boTable;
	private JdbcSelect<T, K> jdbcSelect;
	private JdbcInsert<T, K> jdbcInsert;
	private JdbcUpdate<T, K> jdbcUpdate;
	private JdbcDelete<T, K> jdbcDelete;
	
	@PostConstruct
	public void init() {
		boTable = BoTable.annotationBased(parameterizedClass);
		for (JdbcOperation<T, K> jdbcOperation : Arrays.asList(jdbcSelect, jdbcInsert, jdbcUpdate, jdbcDelete)) {
			jdbcOperation.setBoTable(boTable);
			jdbcOperation.setJdbcTemplate(jdbcTemplate);
		}
	}

	@Override
	public T getById(K id) {
		return jdbcSelect.getById(id);
	}

	@Override
	public K save(T t) {
		return jdbcInsert.insert(t);
	}

	@Override
	public void update(T t) {
		jdbcUpdate.update(t);
	}

	@Override
	public void delete(T t) {
		jdbcDelete.delete(t);
	}

	@Override
	public void deleteById(K id) {
		jdbcDelete.deleteById(id);
	}

	@Override
	public void saveAll(List<T> entities) {
		jdbcInsert.insert(entities);
	}

	@Override
	public void updateAll(List<T> entities) {
		jdbcUpdate.update(entities);
	}

	@Override
	public void deleteAll(List<T> entities) {
		jdbcDelete.delete(entities);
	}

	@Override
	public List<T> findByExample(T example) {
		return jdbcSelect.findByExample(example);
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

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("JdbcDao [jdbcTemplate=");
		builder.append(jdbcTemplate);
		builder.append(", parameterizedClass=");
		builder.append(parameterizedClass);
		builder.append(", boTable=");
		builder.append(boTable);
		builder.append(", jdbcSelect=");
		builder.append(jdbcSelect);
		builder.append(", jdbcInsert=");
		builder.append(jdbcInsert);
		builder.append(", jdbcUpdate=");
		builder.append(jdbcUpdate);
		builder.append(", jdbcDelete=");
		builder.append(jdbcDelete);
		builder.append("]");
		return builder.toString();
	}

}
