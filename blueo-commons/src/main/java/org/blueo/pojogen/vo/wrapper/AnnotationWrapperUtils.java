package org.blueo.pojogen.vo.wrapper;

import java.lang.annotation.Annotation;

import org.blueo.pojogen.vo.wrapper.buildin.ColumnWrapper;
import org.blueo.pojogen.vo.wrapper.buildin.TableWrapper;

public abstract class AnnotationWrapperUtils {

	public static final ColumnWrapper COLUMN_WRAPPER = new ColumnWrapper();
	public static final TableWrapper TABLE_WRAPPER = new TableWrapper();

	public static AnnotationWrapper<Object> simpleWrapper(Class<? extends Annotation> annotationClass) {
		return new AnnotationWrapper<Object>(annotationClass);
	}

}
