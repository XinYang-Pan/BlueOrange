package org.blueo.commons.jdbc;

import java.lang.reflect.Method;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Table;

import org.apache.commons.lang3.StringUtils;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.jdbc.core.ArgumentTypePreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ParameterizedPreparedStatementSetter;
import org.springframework.jdbc.core.StatementCreatorUtils;
import org.springframework.util.Assert;
import org.springframework.util.ReflectionUtils;

import com.google.common.collect.Lists;

public class EntityForJdbc<T> {
	private static final int BATCH_SIZE = 1000;
	private static final String SEPARATOR = ", ";
	// 
	private final Class<T> clazz;
	// 
	private String insertSql;
	private ParameterizedPreparedStatementSetter<T> pss;
	
	public EntityForJdbc(Class<T> clazz) {
		this.clazz = clazz;
		this.init();
	}
	
	private void init() {
		// Is a entity
		Assert.notNull(AnnotationUtils.findAnnotation(clazz, Entity.class));
		// Is a table
		Table table = AnnotationUtils.findAnnotation(clazz, Table.class);
		Assert.notNull(table);
		// prepare variables for building
		String tableName = table.name();
		Method[] methods = clazz.getMethods();
		List<String> columnNames = Lists.newArrayList();
		final List<Method> columMethods = Lists.newArrayList();
		for (Method method : methods) {
			Column column = AnnotationUtils.findAnnotation(method, Column.class);
			GeneratedValue generatedValue = AnnotationUtils.findAnnotation(method, GeneratedValue.class);
			// method has Column, but not GeneratedValue.
			if (column != null && generatedValue == null) {
				String name = column.name();
				if (columnNames.contains(name)) {
					System.out.println("contain same name. ignore. ref="+name);
				} else {
					columnNames.add(name);
					columMethods.add(method);
				}
			}
		}
		Assert.notEmpty(columnNames);
		// real build
		pss = this.buildPss(columMethods);
		insertSql = this.buildInsertSql(tableName, columnNames);
	}

	private String buildInsertSql(String tableName, List<String> columnNames) {
		String columnPart = StringUtils.join(columnNames, SEPARATOR);
		String valuePart = StringUtils.repeat("?", SEPARATOR, columnNames.size());
		return String.format("INSERT INTO %s(%s) VALUES(%s)", tableName, columnPart, valuePart);
	}

	private ParameterizedPreparedStatementSetter<T> buildPss(final List<Method> columMethods) {
		return new ParameterizedPreparedStatementSetter<T>() {

			@Override
			public void setValues(PreparedStatement ps, T t) throws SQLException {
				Object[] args = new String[columMethods.size()];
				int[] argTypes = new int[columMethods.size()];
				
				for (int i = 0; i < columMethods.size(); i++) {
					// ArgumentTypePreparedStatementSetter
					Method method = columMethods.get(i);
					int sqlType = StatementCreatorUtils.javaTypeToSqlParameterType(method.getReturnType());
					Object value = ReflectionUtils.invokeMethod(method, t);
					args[i] = value;
					argTypes[i] = sqlType;
				}
				// 
				ArgumentTypePreparedStatementSetter stmtSetter = new ArgumentTypePreparedStatementSetter(args, argTypes);
				stmtSetter.setValues(ps);;
			}
			
			@Override
			public String toString() {
				return String.valueOf(columMethods);
			}
		};
	}
	
	public void insert(JdbcTemplate jdbcTemplate, List<T> entities) {
		jdbcTemplate.batchUpdate(insertSql, entities, BATCH_SIZE, pss);
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("EntityForJdbc [clazz=");
		builder.append(clazz);
		builder.append(", insertSql=");
		builder.append(insertSql);
		builder.append(", pss=");
		builder.append(pss);
		builder.append("]");
		return builder.toString();
	}
	
}
