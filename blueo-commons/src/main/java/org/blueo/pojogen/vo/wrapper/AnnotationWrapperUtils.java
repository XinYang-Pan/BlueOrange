package org.blueo.pojogen.vo.wrapper;

import java.lang.annotation.Annotation;

public abstract class AnnotationWrapperUtils {

	public static final ColumnWrapper COLUMN_WRAPPER = new ColumnWrapper();
	public static final TableWrapper TABLE_WRAPPER = new TableWrapper();
	
	public static AnnotationWrapper simpleWrapper(Class<? extends Annotation> annotationClass) {
		return new AnnotationWrapper(annotationClass);
	}
	
	public static ColumnWrapper ColumnWrapper() {
		return new ColumnWrapper();
	}
	
}
