package org.blueo.pojogen.bo;

import java.lang.annotation.Annotation;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.blueo.pojogen.bo.wrapper.annotation.AnnotationWrapper;
import org.blueo.pojogen.bo.wrapper.annotation.AnnotationWrapperUtils;
import org.blueo.pojogen.bo.wrapper.clazz.ClassWrapper;

import com.google.common.base.Joiner;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

public class PojoClass extends ValueMapObject {
	private String name;
	private String packageName;
	private ClassWrapper superClass;
	private LinkedHashSet<ClassWrapper> interfaces;
	private List<AnnotationWrapper<PojoClass>> annotationWrappers;
	private List<PojoField> pojoFields;
	
	public Set<ClassWrapper> getImports() {
		Set<ClassWrapper> classes = Sets.newHashSet();
		if (interfaces != null) {
			for (ClassWrapper interface_ : interfaces) {
				classes.addAll(interface_.getImports());
			}
		}
		if (annotationWrappers != null) {
			for (AnnotationWrapper<PojoClass> annotationWrapper : annotationWrappers) {
				classes.addAll(annotationWrapper.getClassWrapper().getImports());
			}
		}
		if (pojoFields != null) {
			for (PojoField pojoField : pojoFields) {
				classes.addAll(pojoField.getImports());
			}
		}
		if (superClass != null) {
			classes.addAll(superClass.getImports());
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
			this.interfaces.add(ClassWrapper.of(class1));
		}
	}

	public void addInterfaces(ClassWrapper... classWrappers) {
		if (classWrappers == null) {
			return;
		}
		if (this.interfaces == null) {
			this.interfaces = Sets.newLinkedHashSet();
		}
		for (ClassWrapper wrapper : classWrappers) {
			this.interfaces.add(wrapper);
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

	public String getFullName() {
		return Joiner.on('.').skipNulls().join(this.getPackageName(), this.getName());
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

	public ClassWrapper getSuperClass() {
		return superClass;
	}

	public void setSuperClass(ClassWrapper superClass) {
		this.superClass = superClass;
	}

	public void setSuperClass(Class<?> superClass) {
		this.superClass = ClassWrapper.of(superClass);
	}

	public LinkedHashSet<ClassWrapper> getInterfaces() {
		return interfaces;
	}

	public void setInterfaces(LinkedHashSet<ClassWrapper> interfaces) {
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
