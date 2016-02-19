package org.blueo.commons.persistent.jdbc.impl;

import java.beans.PropertyDescriptor;
import java.util.List;
import java.util.Objects;

import org.blueo.commons.BlueoUtils;
import org.blueo.commons.persistent.core.dao.Search;
import org.blueo.commons.persistent.jdbc.EntityHelper;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.util.ReflectionUtils;

import com.google.common.base.Joiner;
import com.google.common.collect.Lists;
import com.google.common.reflect.TypeToken;

public class JdbcSearch<T> implements Search<T> {
	//
	private JdbcTemplate jdbcTemplate;
	// 
	@SuppressWarnings("serial")
	private final Class<T> parameterizedClass = BlueoUtils.getParameterizedClass(new TypeToken<T>(this.getClass()) {});
	private final EntityHelper entityHelper = EntityHelper.init(parameterizedClass);
	private final RowMapper<T> rowMapper = new BeanPropertyRowMapper<>(parameterizedClass);

	@Override
	public List<T> findByExample(T example) {
		Objects.requireNonNull(example);
		List<String> whereSqlPieces = Lists.newArrayList();
		List<Object> whereSqlValues = Lists.newArrayList();
		//
		for (PropertyDescriptor pd : entityHelper.getAllCols()) {
			Object value = ReflectionUtils.invokeMethod(pd.getReadMethod(), example);
			if (value != null) {
				whereSqlPieces.add(String.format("%s = ?", pd.getName()));
				whereSqlValues.add(value);
			}
		}
		String sql = String.format("SELECT * FROM %s WHERE %s", entityHelper.getTableName(), Joiner.on(", ").join(whereSqlPieces));
		return jdbcTemplate.query(sql, whereSqlValues.toArray(), rowMapper);
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
		builder.append("EntitySearch [jdbcTemplate=");
		builder.append(jdbcTemplate);
		builder.append(", entityHelper=");
		builder.append(entityHelper);
		builder.append(", rowMapper=");
		builder.append(rowMapper);
		builder.append("]");
		return builder.toString();
	}

}
