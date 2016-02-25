package org.blueo.commons.persistent.jdbc.impl;

import java.beans.PropertyDescriptor;
import java.util.List;
import java.util.Objects;

import javax.annotation.PostConstruct;

import org.blueo.commons.BlueoUtils;
import org.blueo.commons.persistent.core.dao.po.id.HasId;
import org.blueo.commons.persistent.jdbc.util.BoColumn;
import org.blueo.commons.persistent.jdbc.util.ColumnRowMapper;
import org.springframework.util.ReflectionUtils;

import com.google.common.base.Joiner;
import com.google.common.collect.Lists;

public class JdbcSelect<T extends HasId<K>, K> extends JdbcOperation<T, K> {
	//
	private ColumnRowMapper<T> rowMapper;

	@PostConstruct
	public void init() {
		rowMapper = new ColumnRowMapper<>(boTable);
	}

	public T getById(K id) {
		String selectSqlBeforeWhere = rowMapper.getSelectSqlBeforeWhere();
		String idColumnName = boTable.getIdCol().getColumnName();
		String sql = String.format("%s WHERE % = ?", selectSqlBeforeWhere, idColumnName);
		return BlueoUtils.oneOrNull(jdbcTemplate.query(sql, new Object[] { id }, rowMapper));
	}

	public List<T> findByExample(T example) {
		Objects.requireNonNull(example);
		List<String> whereSqlPieces = Lists.newArrayList();
		List<Object> whereSqlValues = Lists.newArrayList();
		//
		for (BoColumn boColumn : boTable.getAllCols()) {
			PropertyDescriptor pd = boColumn.getPropertyDescriptor();
			Object value = ReflectionUtils.invokeMethod(pd.getReadMethod(), example);
			if (value != null) {
				whereSqlPieces.add(String.format("%s = ?", boColumn.getColumnName()));
				whereSqlValues.add(value);
			}
		}
		// 
		String selectSqlBeforeWhere = rowMapper.getSelectSqlBeforeWhere();
		String sql = String.format("%s WHERE %s", selectSqlBeforeWhere, Joiner.on(", ").join(whereSqlPieces));
		return jdbcTemplate.query(sql, whereSqlValues.toArray(), rowMapper);
	}
	
}
