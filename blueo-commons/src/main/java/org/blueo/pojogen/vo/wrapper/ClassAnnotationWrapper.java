package org.blueo.pojogen.vo.wrapper;

import java.lang.annotation.Annotation;

import org.blueo.pojogen.vo.PojoClass;

public class ClassAnnotationWrapper extends AnnotationWrapper<PojoClass> {

	public ClassAnnotationWrapper(Class<? extends Annotation> annotationClass) {
		super(annotationClass);
	}

	@Override
	public String getDisplayString(PojoClass t) {
		return String.format("@%s", annotationClass.getSimpleName());
	}

}
