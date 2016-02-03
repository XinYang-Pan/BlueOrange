package org.blueo.pojogen.vo.wrapper;

import java.lang.annotation.Annotation;

import org.blueo.pojogen.vo.PojoField;

public class FieldAnnotationWrapper extends AnnotationWrapper<PojoField> {

	public FieldAnnotationWrapper(Class<? extends Annotation> annotationClass) {
		super(annotationClass);
	}

	@Override
	public String getDisplayString(PojoField t) {
		return String.format("@%s", annotationClass.getSimpleName());
	}

}
