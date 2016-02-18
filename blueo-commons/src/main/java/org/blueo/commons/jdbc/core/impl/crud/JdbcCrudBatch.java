package org.blueo.commons.jdbc.core.impl.crud;

import java.util.List;

import org.blueo.commons.jdbc.EntityWrapper;
import org.blueo.commons.jdbc.core.CrudBatch;
import org.blueo.commons.jdbc.core.impl.ParameterizedClass;
import org.springframework.jdbc.core.JdbcTemplate;

public class JdbcCrudBatch<T, K> implements CrudBatch<T, K> {
	protected ParameterizedClass<T> parameterizedClass = new ParameterizedClass<T>(){};
	private EntityWrapper<T> entityWrapper = EntityWrapper.of(parameterizedClass.getParameterizedClass());
	
	private JdbcTemplate jdbcTemplate;
	
	// fetching ids is not support for this impl
	@Override
	public void saveAll(List<T> list) {
		entityWrapper.saveAll(jdbcTemplate, list);
	}

	@Override
	public void updateAll(List<T> list) {
		entityWrapper.updateAll(jdbcTemplate, list);
	}

	@Override
	public void deleteAll(List<T> list) {
		entityWrapper.deleteAll(jdbcTemplate, list);
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
