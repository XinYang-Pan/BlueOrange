package org.blueo.commons.persistent.jdbc;

import java.beans.PropertyDescriptor;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.apache.commons.lang3.StringUtils;
import org.springframework.core.annotation.AnnotationUtils;

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

	public static String buildInsertSql(String tableName, List<String> columnNames) {
		String columnPart = StringUtils.join(columnNames, SEPARATOR);
		String valuePart = StringUtils.repeat("?", SEPARATOR, columnNames.size());
		return String.format("INSERT INTO %s(%s) VALUES(%s)", tableName, columnPart, valuePart);
	}

	public static String buildUpdateSql(String tableName, List<String> columnNames, String idColumnName) {
		List<String> setPiece = Lists.newArrayList();
		for (String columnName : columnNames) {
			setPiece.add(String.format("%s=?", columnName));
		}
		String setPart = StringUtils.join(setPiece, SEPARATOR);
		return String.format("UPDATE %s SET %s WHERE %s=?", tableName, setPart, idColumnName);
	}

	public static String buildDeleteSql(String tableName, String idColumnName) {
		return String.format("DELETE FROM %s WHERE %s=?", tableName, idColumnName);
	}
	
}
