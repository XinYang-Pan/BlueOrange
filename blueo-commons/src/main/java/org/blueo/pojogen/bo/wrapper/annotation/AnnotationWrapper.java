package org.blueo.pojogen.bo.wrapper.annotation;

import java.lang.annotation.Annotation;

import org.blueo.pojogen.bo.wrapper.clazz.ClassWrapper;

import com.google.common.base.Function;

public class AnnotationWrapper<T> {
	protected final ClassWrapper classWrapper;
	// should be BiFunction in JDK 8
	protected final Function<T, String> function;
	
	// -----------------------------
	// ----- Constructors
	// -----------------------------
	public AnnotationWrapper(Class<? extends Annotation> annotationClass) {
		this(annotationClass, null);
	}

	public AnnotationWrapper(Class<? extends Annotation> annotationClass, Function<T, String> function) {
		this(ClassWrapper.of(annotationClass), function);
	}

	public AnnotationWrapper(ClassWrapper classWrapper) {
		this(classWrapper, null);
	}

	public AnnotationWrapper(ClassWrapper classWrapper, Function<T, String> function) {
		super();
		this.classWrapper = classWrapper;
		this.function = function;
	}

	// -----------------------------
	// ----- Others
	// -----------------------------
	public String getDisplayString(T t) {
		if (function == null) {
			return getAnnotationClassPart();
		} else {
			return String.format("%s%s", this.classWrapper, function.apply(t));
		}
	}

	protected String getAnnotationClassPart() {
		return String.format("@%s", classWrapper.getName());
	}

	public ClassWrapper getClassWrapper() {
		return classWrapper;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("AnnotationWrapper [annotationClass=");
		builder.append(classWrapper);
		builder.append("]");
		return builder.toString();
	}

}
