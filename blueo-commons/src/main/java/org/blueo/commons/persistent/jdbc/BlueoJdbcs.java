package org.blueo.commons.persistent.jdbc;

import java.beans.PropertyDescriptor;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.apache.commons.lang3.StringUtils;
import org.blueo.commons.BlueoUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.jdbc.core.ArgumentTypePreparedStatementSetter;
import org.springframework.jdbc.core.ParameterizedPreparedStatementSetter;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.StatementCreatorUtils;
import org.springframework.util.ReflectionUtils;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;

public class BlueoJdbcs {
	private static final String SEPARATOR = ", ";
	
	// -----------------------------
	// ----- PropertyDescriptor Utils
	// -----------------------------
	
	public static List<String> getColumnNames(List<PropertyDescriptor> pds) {
		List<String> columnNames = Lists.newArrayList();
		for (PropertyDescriptor pd : pds) {
			columnNames.add(BlueoJdbcs.getColumnName(pd));
		}
		return columnNames;
	}
	
	public static String getColumnName(PropertyDescriptor pd) {
		Column column = getAnnotation(pd, Column.class);
		return column.name();
	}
	
	public static boolean isColumn(PropertyDescriptor pd) {
		return hasAnnotation(pd, Column.class);
	}
	
	public static boolean isGeneratedValue(PropertyDescriptor pd) {
		return hasAnnotation(pd, GeneratedValue.class);
	}
	
	public static boolean isId(PropertyDescriptor pd) {
		return hasAnnotation(pd, Id.class);
	}

	public static boolean hasAnnotation(PropertyDescriptor pd, Class<? extends Annotation> annotationType) {
		Annotation annotation = getAnnotation(pd, annotationType);
		return annotation != null;
	}

	public static <T extends Annotation> T getAnnotation(PropertyDescriptor pd, Class<T> annotationType) {
		Method readMethod = pd.getReadMethod();
		Preconditions.checkNotNull(readMethod);
		T column = AnnotationUtils.findAnnotation(readMethod, annotationType);
		return column;
	}
	
	// -----------------------------
	// ----- sqls
	// -----------------------------

	public static String buildInsertSql(String tableName, List<PropertyDescriptor> columnPds) {
		List<String> columnNames = BlueoJdbcs.getColumnNames(columnPds);
		String columnPart = StringUtils.join(columnNames, SEPARATOR);
		String valuePart = StringUtils.repeat("?", SEPARATOR, columnNames.size());
		return String.format("INSERT INTO %s(%s) VALUES(%s)", tableName, columnPart, valuePart);
	}

	public static String buildUpdateSql(String tableName, List<PropertyDescriptor> noneIds, PropertyDescriptor id) {
		List<String> setPiece = Lists.newArrayList();
		for (String columnName : BlueoJdbcs.getColumnNames(noneIds)) {
			setPiece.add(String.format("%s=?", columnName));
		}
		String setPart = StringUtils.join(setPiece, SEPARATOR);
		return String.format("UPDATE %s SET %s WHERE %s=?", tableName, setPart, BlueoJdbcs.getColumnName(id));
	}

	public static String buildDeleteSql(String tableName, PropertyDescriptor id) {
		return String.format("DELETE FROM %s WHERE %s=?", tableName, BlueoJdbcs.getColumnName(id));
	}

	// -----------------------------
	// ----- ParameterizedPreparedStatementSetter
	// -----------------------------

	public static <T> ParameterizedPreparedStatementSetter<T> buildInsertPss(final List<PropertyDescriptor> columnPds) {
		return new ParameterizedPreparedStatementSetter<T>() {

			@Override
			public void setValues(PreparedStatement ps, T t) throws SQLException {
				Object[] args = new Object[columnPds.size()];
				int[] argTypes = new int[columnPds.size()];

				for (int i = 0; i < columnPds.size(); i++) {
					// ArgumentTypePreparedStatementSetter
					PropertyDescriptor pd = columnPds.get(i);
					// 
					argTypes[i] = StatementCreatorUtils.javaTypeToSqlParameterType(pd.getPropertyType());
					// 
					Method readMethod = pd.getReadMethod();
					Object value = ReflectionUtils.invokeMethod(readMethod, t);
					args[i] = getValue(value, readMethod);
				}
				//
				ArgumentTypePreparedStatementSetter stmtSetter = new ArgumentTypePreparedStatementSetter(args, argTypes);
				stmtSetter.setValues(ps);
			}

			@Override
			public String toString() {
				StringBuilder builder = new StringBuilder();
				builder.append("InsertPss [columnPds=");
				builder.append(columnPds);
				builder.append("]");
				return builder.toString();
			}
			
		};
	}

	public static <T> ParameterizedPreparedStatementSetter<T> buildUpdatePss(final List<PropertyDescriptor> noneIds, final PropertyDescriptor id) {
		return new ParameterizedPreparedStatementSetter<T>() {

			@Override
			public void setValues(PreparedStatement ps, T t) throws SQLException {
				List<PropertyDescriptor> allColumns = Lists.newArrayList();
				allColumns.addAll(noneIds);
				allColumns.add(id);
				//
				Object[] args = new Object[allColumns.size() + 1];
				int[] argTypes = new int[allColumns.size() + 1];
				for (int i = 0; i < allColumns.size(); i++) {
					// ArgumentTypePreparedStatementSetter
					PropertyDescriptor pd = allColumns.get(i);
					// 
					argTypes[i] = StatementCreatorUtils.javaTypeToSqlParameterType(pd.getPropertyType());
					// 
					Method readMethod = pd.getReadMethod();
					Object value = ReflectionUtils.invokeMethod(readMethod, t);
					args[i] = getValue(value, readMethod);
				}
				//
				ArgumentTypePreparedStatementSetter stmtSetter = new ArgumentTypePreparedStatementSetter(args, argTypes);
				stmtSetter.setValues(ps);
			}

			@Override
			public String toString() {
				StringBuilder builder = new StringBuilder();
				builder.append("UpdatePss [noneIds=");
				builder.append(noneIds);
				builder.append(", id=");
				builder.append(id);
				builder.append("]");
				return builder.toString();
			}
			
		};
	}

	public static <T> ParameterizedPreparedStatementSetter<T> buildDeletePss(final PropertyDescriptor idCol) {
		return new ParameterizedPreparedStatementSetter<T>() {

			@Override
			public void setValues(PreparedStatement ps, T t) throws SQLException {
				//
				Object[] args = new Object[1];
				int[] argTypes = new int[1];
				// 
				argTypes[0] = StatementCreatorUtils.javaTypeToSqlParameterType(idCol.getPropertyType());
				// 
				Method readMethod = idCol.getReadMethod();
				Object value = ReflectionUtils.invokeMethod(readMethod, t);
				args[0] = getValue(value, readMethod);
				//
				ArgumentTypePreparedStatementSetter stmtSetter = new ArgumentTypePreparedStatementSetter(args, argTypes);
				stmtSetter.setValues(ps);
			}

			@Override
			public String toString() {
				StringBuilder builder = new StringBuilder();
				builder.append("DeletePss [idCol=");
				builder.append(idCol);
				builder.append("]");
				return builder.toString();
			}
			
		};
	}
	
	private static Object getValue(Object value, Method method) {
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

	// -----------------------------
	// ----- ParameterizedPreparedStatementSetter
	// -----------------------------
	// TODO Object object = rs.getObject(columnName); need to look bean wrapper
	public static <T> RowMapper<T> getColumnNameRowMapper(final Map<String, PropertyDescriptor> columnName2pdMap, final Class<T> clazz) {
		return new RowMapper<T>() {

			@Override
			public T mapRow(ResultSet rs, int rowNum) throws SQLException {
				T t = BeanUtils.instantiate(clazz);
				while (rs.next()) {
					for (Entry<String, PropertyDescriptor> entry : columnName2pdMap.entrySet()) {
						String columnName = entry.getKey();
						PropertyDescriptor pd = entry.getValue();
						Object object = rs.getObject(columnName);
						ReflectionUtils.invokeMethod(pd.getWriteMethod(), t, object);
					}
				}
				return t;
			}
		};
	}
	
}
