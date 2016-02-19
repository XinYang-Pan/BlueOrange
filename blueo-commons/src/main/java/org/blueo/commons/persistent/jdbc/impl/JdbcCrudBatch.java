package org.blueo.commons.persistent.jdbc.impl;

import java.util.List;

import org.blueo.commons.BlueoUtils;
import org.blueo.commons.persistent.core.dao.CrudBatch;
import org.blueo.commons.persistent.jdbc.BlueoJdbcs;
import org.blueo.commons.persistent.jdbc.EntityHelper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ParameterizedPreparedStatementSetter;

import com.google.common.reflect.TypeToken;

public class JdbcCrudBatch<T, K> implements CrudBatch<T, K> {
	private static final int BATCH_SIZE = 1000;
	// DI
	private JdbcTemplate jdbcTemplate;
	// 
	@SuppressWarnings("serial")
	private final Class<T> parameterizedClass = BlueoUtils.getParameterizedClass(new TypeToken<T>(this.getClass()) {});
	private final EntityHelper entityHelper = EntityHelper.init(parameterizedClass);;
	//
	private String insertSql;
	private String updateSql;
	private String deleteSql;
	private ParameterizedPreparedStatementSetter<T> insertPss;
	private ParameterizedPreparedStatementSetter<T> updatePss;
	private ParameterizedPreparedStatementSetter<T> deletePss;
	
	public JdbcCrudBatch() {
		this.init();
	}
	
	private void init() {
		// insert
		insertPss = BlueoJdbcs.buildInsertPss(entityHelper.getNoneGenValueCols());
		insertSql = BlueoJdbcs.buildInsertSql(entityHelper.getTableName(), entityHelper.getNoneGenValueCols());
		// update
		updatePss = BlueoJdbcs.buildUpdatePss(entityHelper.getNoneIdCols(), entityHelper.getIdCol());
		updateSql = BlueoJdbcs.buildUpdateSql(entityHelper.getTableName(), entityHelper.getNoneIdCols(), entityHelper.getIdCol());
		// delete
		deletePss = BlueoJdbcs.buildDeletePss(entityHelper.getIdCol());
		deleteSql = BlueoJdbcs.buildDeleteSql(entityHelper.getTableName(), entityHelper.getIdCol());
	}

	public void saveAll(List<T> entities) {
		jdbcTemplate.batchUpdate(insertSql, entities, BATCH_SIZE, insertPss);
	}

	public void updateAll(List<T> entities) {
		jdbcTemplate.batchUpdate(updateSql, entities, BATCH_SIZE, updatePss);
	}

	public void deleteAll(List<T> entities) {
		jdbcTemplate.batchUpdate(deleteSql, entities, BATCH_SIZE, deletePss);
	}

	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}

	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("EntityWrapper [jdbcTemplate=");
		builder.append(jdbcTemplate);
		builder.append(", entityHelper=");
		builder.append(entityHelper);
		builder.append(", insertSql=");
		builder.append(insertSql);
		builder.append(", updateSql=");
		builder.append(updateSql);
		builder.append(", deleteSql=");
		builder.append(deleteSql);
		builder.append(", insertPss=");
		builder.append(insertPss);
		builder.append(", updatePss=");
		builder.append(updatePss);
		builder.append(", deletePss=");
		builder.append(deletePss);
		builder.append("]");
		return builder.toString();
	}

}
