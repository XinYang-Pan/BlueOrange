package org.blueo.commons.persistent.jdbc.impl;

import java.util.Arrays;
import java.util.List;

import javax.annotation.PostConstruct;

import org.blueo.commons.persistent.dao.impl.AbstractEntityDao;
import org.blueo.commons.persistent.entity.EntityTable;
import org.springframework.jdbc.core.JdbcTemplate;

public class JdbcDao<T, K> extends AbstractEntityDao<T, K> {
	// DI
	private JdbcTemplate jdbcTemplate;
	// 
	private EntityTable<T> entityTable;
	private JdbcSelect<T, K> jdbcSelect = new JdbcSelect<>();
	private JdbcInsert<T, K> jdbcInsert = new JdbcInsert<>();
	private JdbcUpdate<T, K> jdbcUpdate = new JdbcUpdate<>();
	private JdbcDelete<T, K> jdbcDelete = new JdbcDelete<>();
	
	public JdbcDao() {
		super();
	}

	public JdbcDao(Class<T> parameterizedClass) {
		super(parameterizedClass);
	}

	@PostConstruct
	public void init() {
		entityTable = EntityTable.annotationBased(parameterizedClass);
		for (JdbcOperation<T, K> jdbcOperation : Arrays.asList(jdbcSelect, jdbcInsert, jdbcUpdate, jdbcDelete)) {
			jdbcOperation.setBoTable(entityTable);
			jdbcOperation.setIdHandler(idHandler);
			jdbcOperation.setJdbcTemplate(jdbcTemplate);
			jdbcOperation.init();
		}
	}

	public List<T> getAll() {
		return jdbcSelect.getAll();
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

}
