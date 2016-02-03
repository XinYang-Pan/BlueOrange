package org.blueo.pojogen.vo.wrapper;

import java.lang.annotation.Annotation;

public class AnnotationWrapper<T> {
	protected Class<? extends Annotation> annotationClass;

	public AnnotationWrapper(Class<? extends Annotation> annotationClass) {
		super();
		this.annotationClass = annotationClass;
	}

	public String getDisplayString(T t) {
		return String.format("@%s", annotationClass.getSimpleName());
	}

	public Class<? extends Annotation> getAnnotationClass() {
		return annotationClass;
	}

	public void setAnnotationClass(Class<? extends Annotation> annotationClass) {
		this.annotationClass = annotationClass;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("AnnotationWrapper [annotationClass=");
		builder.append(annotationClass);
		builder.append("]");
		return builder.toString();
	}

}
