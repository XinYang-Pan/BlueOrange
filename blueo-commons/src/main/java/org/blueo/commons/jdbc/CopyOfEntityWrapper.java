package org.blueo.commons.jdbc;

import java.lang.reflect.Method;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.apache.commons.lang3.StringUtils;
import org.blueo.commons.BlueoUtils;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.jdbc.core.ArgumentTypePreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ParameterizedPreparedStatementSetter;
import org.springframework.jdbc.core.StatementCreatorUtils;
import org.springframework.util.Assert;
import org.springframework.util.ReflectionUtils;

import com.google.common.base.Objects;
import com.google.common.collect.Lists;

public class CopyOfEntityWrapper<T> {
	private static final int BATCH_SIZE = 1000;
	private static final String SEPARATOR = ", ";
	//
	private final Class<T> clazz;
	//
	private String insertSql;
	private String updateSql;
	private ParameterizedPreparedStatementSetter<T> insertPss;
	private ParameterizedPreparedStatementSetter<T> updatePss;
	
	public static <T> CopyOfEntityWrapper<T> of(Class<T> clazz) {
		return new CopyOfEntityWrapper<>(clazz);
	}
	
	public CopyOfEntityWrapper(Class<T> clazz) {
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
		final List<Method> columnMethods = Lists.newArrayList();
		String idColumnName = null;
		for (Method method : methods) {
			Column column = AnnotationUtils.findAnnotation(method, Column.class);
			GeneratedValue generatedValue = AnnotationUtils.findAnnotation(method, GeneratedValue.class);
			Id id = AnnotationUtils.findAnnotation(method, Id.class);
			// method has Column, but not GeneratedValue.
			if (column != null && generatedValue == null) {
				String name = column.name();
				if (columnNames.contains(name)) {
					System.out.println("contain same name. ignore. ref=" + name);
				} else {
					columnNames.add(name);
					columnMethods.add(method);
					if (id != null) {
						idColumnName = name;
					}
				}
			}
		}
		Assert.notEmpty(columnNames);
		// real build
		insertPss = this.buildInsertPss(columnMethods);
		insertSql = this.buildInsertSql(tableName, columnNames);
		updateSql = this.buildUpdateSql(tableName, columnNames, idColumnName);
	}

	private String buildInsertSql(String tableName, List<String> columnNames) {
		String columnPart = StringUtils.join(columnNames, SEPARATOR);
		String valuePart = StringUtils.repeat("?", SEPARATOR, columnNames.size());
		return String.format("INSERT INTO %s(%s) VALUES(%s)", tableName, columnPart, valuePart);
	}

	private String buildUpdateSql(String tableName, List<String> columnNames, String idColumnName) {
		List<String> setPiece = Lists.newArrayList();
		for (String columnName : columnNames) {
			if (!Objects.equal(columnName, idColumnName)) {
				setPiece.add(String.format("%s=?", columnName));
			}
		}
		String setPart = StringUtils.join(setPiece, SEPARATOR);
		return String.format("UPDATE %s SET %s WHERE %s=?", tableName, setPart, idColumnName);
	}

	private Object getValue(Object value, Method method) {
		Enumerated enumerated = AnnotationUtils.findAnnotation(method, Enumerated.class);
		if (!(value instanceof Enum<?>)) {
			return value;
		}
		Enum<?> enumValue = (Enum<?>) value;
		if (enumerated == null || enumerated.value() == null || enumerated.value() == EnumType.STRING) {
			return enumValue.toString();
		} else if (enumerated.value() == EnumType.ORDINAL) {
			return enumValue.ordinal();
		} else {
			throw BlueoUtils.illegalArgument("never go here.");
		}
	}

	private ParameterizedPreparedStatementSetter<T> buildInsertPss(final List<Method> columnMethods) {
		return new ParameterizedPreparedStatementSetter<T>() {

			@Override
			public void setValues(PreparedStatement ps, T t) throws SQLException {
				Object[] args = new Object[columnMethods.size()];
				int[] argTypes = new int[columnMethods.size()];

				for (int i = 0; i < columnMethods.size(); i++) {
					// ArgumentTypePreparedStatementSetter
					Method method = columnMethods.get(i);
					int sqlType = StatementCreatorUtils.javaTypeToSqlParameterType(method.getReturnType());
					Object value = ReflectionUtils.invokeMethod(method, t);
					args[i] = getValue(value, method);
					argTypes[i] = sqlType;
				}
				//
				ArgumentTypePreparedStatementSetter stmtSetter = new ArgumentTypePreparedStatementSetter(args, argTypes);
				stmtSetter.setValues(ps);
				;
			}

			@Override
			public String toString() {
				return String.valueOf(columnMethods);
			}
		};
	}

	private ParameterizedPreparedStatementSetter<T> buildUpdatePss(final List<Method> columnMethods, Method idColumnMethod) {
		return new ParameterizedPreparedStatementSetter<T>() {

			@Override
			public void setValues(PreparedStatement ps, T t) throws SQLException {
				Object[] args = new Object[columnMethods.size()];
				int[] argTypes = new int[columnMethods.size()];

				for (int i = 0; i < columnMethods.size(); i++) {
					// ArgumentTypePreparedStatementSetter
					Method method = columnMethods.get(i);
					int sqlType = StatementCreatorUtils.javaTypeToSqlParameterType(method.getReturnType());
					Object value = ReflectionUtils.invokeMethod(method, t);
					args[i] = getValue(value, method);
					argTypes[i] = sqlType;
				}
				//
				ArgumentTypePreparedStatementSetter stmtSetter = new ArgumentTypePreparedStatementSetter(args, argTypes);
				stmtSetter.setValues(ps);
				;
			}

			@Override
			public String toString() {
				return String.valueOf(columnMethods);
			}
		};
	}

	public void saveAll(JdbcTemplate jdbcTemplate, List<T> entities) {
		jdbcTemplate.batchUpdate(insertSql, entities, BATCH_SIZE, insertPss);
	}

	public void updateAll(JdbcTemplate jdbcTemplate, List<T> entities) {
		jdbcTemplate.batchUpdate(updateSql, entities, BATCH_SIZE, insertPss);
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("EntityForJdbc [clazz=");
		builder.append(clazz);
		builder.append(", insertSql=");
		builder.append(insertSql);
		builder.append(", pss=");
		builder.append(insertPss);
		builder.append("]");
		return builder.toString();
	}

}
