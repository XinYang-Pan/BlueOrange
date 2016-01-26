package org.blueo.codegen;

import java.lang.reflect.Method;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Table;

import org.apache.commons.lang3.StringUtils;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.util.Assert;

import com.google.common.collect.Lists;

public abstract class JdbcGenerator {
	
	private static final String SEPARATOR = ", ";

	public static void generateInsertFromEntity(Class<?> clazz) {
		// Has Entity
		Assert.notNull(AnnotationUtils.findAnnotation(clazz, Entity.class));
		// 
		Table table = AnnotationUtils.findAnnotation(clazz, Table.class);
		Assert.notNull(table);
		String tableName = table.name();
		Method[] methods = clazz.getMethods();
		List<String> columnNames = Lists.newArrayList();
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
				}
			}
		}
		Assert.notEmpty(columnNames);
		String columnPart = StringUtils.join(columnNames, SEPARATOR);
		String valuePart = StringUtils.repeat("?", SEPARATOR, columnNames.size());
		System.out.format("INSERT INTO %s(%s) VALUES(%s)", tableName, columnPart, valuePart);
	}
	
}
