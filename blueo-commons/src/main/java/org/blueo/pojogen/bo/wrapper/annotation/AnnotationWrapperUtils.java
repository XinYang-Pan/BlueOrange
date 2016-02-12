package org.blueo.pojogen.bo.wrapper.annotation;

import java.lang.annotation.Annotation;

import org.blueo.pojogen.bo.wrapper.annotation.buildin.ColumnWrapper;
import org.blueo.pojogen.bo.wrapper.annotation.buildin.TableWrapper;

public abstract class AnnotationWrapperUtils {

	public static final ColumnWrapper COLUMN_WRAPPER = new ColumnWrapper();
	public static final TableWrapper TABLE_WRAPPER = new TableWrapper();

	public static <T> AnnotationWrapper<T> simpleWrapper(Class<? extends Annotation> annotationClass) {
		return new AnnotationWrapper<T>(annotationClass);
	}

}
