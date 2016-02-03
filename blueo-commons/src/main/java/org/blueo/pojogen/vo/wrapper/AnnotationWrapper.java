package org.blueo.pojogen.vo.wrapper;

import java.lang.annotation.Annotation;

import com.google.common.base.Function;

public class AnnotationWrapper<T> {
	protected final Class<? extends Annotation> annotationClass;
	// should be BiFunction in JDK 8
	protected final Function<T, String> function;

	public AnnotationWrapper(Class<? extends Annotation> annotationClass) {
		this(annotationClass, null);
	}

	public AnnotationWrapper(Class<? extends Annotation> annotationClass, Function<T, String> function) {
		super();
		this.annotationClass = annotationClass;
		this.function = function;
	}

	public String getDisplayString(T t) {
		if (function == null) {
			return getAnnotationClassPart();
		} else {
			return String.format("%s%s", this.annotationClass, function.apply(t));
		}
	}

	protected String getAnnotationClassPart() {
		return String.format("@%s", annotationClass.getSimpleName());
	}

	public Class<? extends Annotation> getAnnotationClass() {
		return annotationClass;
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
