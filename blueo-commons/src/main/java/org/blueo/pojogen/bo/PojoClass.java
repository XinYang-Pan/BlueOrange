package org.blueo.pojogen.bo;

import java.lang.annotation.Annotation;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.blueo.pojogen.bo.wrapper.AnnotationWrapper;
import org.blueo.pojogen.bo.wrapper.AnnotationWrapperUtils;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

public class PojoClass extends ValueMapObject {
	private String name;
	private String packageName;
	private Class<?> superClass;
	private LinkedHashSet<Class<?>> interfaces;
	private List<AnnotationWrapper<PojoClass>> annotationWrappers;
	private List<PojoField> pojoFields;
	
	public Set<Class<?>> getClasses() {
		Set<Class<?>> classes = Sets.newHashSet();
		if (interfaces != null) {
			for (Class<?> interface_ : interfaces) {
				classes.add(interface_);
			}
		}
		if (annotationWrappers != null) {
			for (AnnotationWrapper<PojoClass> wrapper : annotationWrappers) {
				classes.add(wrapper.getAnnotationClass());
			}
		}
		if (pojoFields != null) {
			for (PojoField pojoField : pojoFields) {
				classes.addAll(pojoField.getClasses());
			}
		}
		if (superClass != null) {
			classes.add(superClass);
		}
		return classes;
	}

	public void addInterfaces(Class<?>... interfaces) {
		if (interfaces == null) {
			return;
		}
		if (this.interfaces == null) {
			this.interfaces = Sets.newLinkedHashSet();
		}
		for (Class<?> class1 : interfaces) {
			this.interfaces.add(class1);
		}
	}

	public PojoClass addAnnotation(Class<? extends Annotation> annotation) {
		if (annotation == null) {
			return this;
		}
		AnnotationWrapper<PojoClass> wrapper = AnnotationWrapperUtils.simpleWrapper(annotation);
		this.addAnnotationWrapper(wrapper);
		return this;
	}

	public PojoClass addAnnotationWrapper(AnnotationWrapper<PojoClass> annotationWrapper) {
		if (annotationWrapper == null) {
			return this;
		}
		if (this.annotationWrappers == null) {
			this.annotationWrappers = Lists.newArrayList();
		}
		this.annotationWrappers.add(annotationWrapper);
		return this;
	}

	public void addAnnotationWrappers(AnnotationWrapper<PojoClass>[] annotationWrappers) {
		if (annotationWrappers == null) {
			return;
		}
		for (AnnotationWrapper<PojoClass> wrapper : annotationWrappers) {
			this.addAnnotationWrapper(wrapper);
		}
	}

	// --------------------------------------
	// ---- Getter Setter toString
	// --------------------------------------

	public String getPackageName() {
		return packageName;
	}

	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}

	public Class<?> getSuperClass() {
		return superClass;
	}

	public void setSuperClass(Class<?> superClass) {
		this.superClass = superClass;
	}

	public LinkedHashSet<Class<?>> getInterfaces() {
		return interfaces;
	}

	public void setInterfaces(LinkedHashSet<Class<?>> interfaces) {
		this.interfaces = interfaces;
	}

	public List<AnnotationWrapper<PojoClass>> getAnnotationWrappers() {
		return annotationWrappers;
	}

	public void setAnnotationWrappers(List<AnnotationWrapper<PojoClass>> annotationWrappers) {
		this.annotationWrappers = annotationWrappers;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<PojoField> getEntityFields() {
		return pojoFields;
	}

	public void setEntityFields(List<PojoField> pojoFields) {
		this.pojoFields = pojoFields;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("PojoClass [name=");
		builder.append(name);
		builder.append(", packageName=");
		builder.append(packageName);
		builder.append(", superClass=");
		builder.append(superClass);
		builder.append(", interfaces=");
		builder.append(interfaces);
		builder.append(", annotationWrappers=");
		builder.append(annotationWrappers);
		builder.append(", pojoFields=");
		builder.append(pojoFields);
		builder.append("]");
		return builder.toString();
	}

}
