package org.blueo.commons.jdbc;

import java.beans.PropertyDescriptor;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.springframework.core.annotation.AnnotationUtils;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;

public class EntityWrapperUtils {

	public static List<String> getColumnNames(List<PropertyDescriptor> pds) {
		List<String> columnNames = Lists.newArrayList();
		for (PropertyDescriptor pd : pds) {
			columnNames.add(EntityWrapperUtils.getColumnName(pd));
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
	
}
