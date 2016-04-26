package org.blueo.commons.persistent.entity;

import java.beans.PropertyDescriptor;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

import org.blueo.commons.persistent.dao.po.id.HasId;
import org.blueo.commons.persistent.dao.po.id.HasIdHandler;
import org.blueo.commons.persistent.dao.po.id.IdHandler;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.util.ReflectionUtils;

import com.google.common.base.Preconditions;

public class EntityUtils {

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static <T, K> IdHandler<T, K> idGetter(Class<T> clazz) {
		Preconditions.checkNotNull(clazz);
		if (HasId.class.isAssignableFrom(clazz)) {
			return new HasIdHandler();
		}
		final EntityColumn idCol = EntityTable.annotationBased(clazz).getIdCol();
		return new IdHandler<T, K>() {

			@Override
			public K getId(T t) {
				Method readMethod = idCol.getPropertyDescriptor().getReadMethod();
				return (K) ReflectionUtils.invokeMethod(readMethod, t);
			}

			@Override
			public void setId(T t, K k) {
				Method writeMethod = idCol.getPropertyDescriptor().getWriteMethod();
				ReflectionUtils.invokeMethod(writeMethod, t, k);
			}
		};
	}

	public static String getColumnName(PropertyDescriptor pd) {
		Column column = getAnnotation(pd, Column.class);
		return column.name();
	}

	public static String getSequenceName(PropertyDescriptor pd) {
		SequenceGenerator sequenceGenerator = getAnnotation(pd, SequenceGenerator.class);
		if (sequenceGenerator != null) {
			return sequenceGenerator.sequenceName();
		} else {
			return null;
		}
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
