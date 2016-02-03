package org.blueo.pojogen.vo.wrapper;

import java.lang.annotation.Annotation;

public class AnnotationWrapper<A extends Annotation, T> {
	protected Class<A> annotationClass;

	public AnnotationWrapper(Class<A> annotationClass) {
		super();
		this.annotationClass = annotationClass;
	}
	
	public String getDisplayString(T t) {
		return String.format("@%s", annotationClass.getSimpleName());
	}
	
	public Class<A> getAnnotationClass() {
		return annotationClass;
	}

	public void setAnnotationClass(Class<A> annotationClass) {
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
