package org.blueo.pojogen.bo.wrapper.annotation;

import java.lang.annotation.Annotation;

import org.blueo.pojogen.bo.PojoField;

public class FieldAnnotationWrapper extends AnnotationWrapper<PojoField> {

	public FieldAnnotationWrapper(Class<? extends Annotation> annotationClass) {
		super(annotationClass);
	}

	@Override
	public String getDisplayString(PojoField t) {
		return String.format("@%s", classWrapper.getName());
	}

}
