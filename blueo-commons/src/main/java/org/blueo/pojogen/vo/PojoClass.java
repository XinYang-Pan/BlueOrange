package org.blueo.pojogen.vo;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.blueo.pojogen.vo.wrapper.AnnotationWrapper;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;

public class PojoClass {
	private String packageName;
	private Class<?> superClass;
	private LinkedHashSet<Class<?>> interfaces;
	private List<AnnotationWrapper<PojoClass>> annotationWrappers;
	private String name;
	private PojoField id;
	private List<PojoField> pojoFields;
	private final Map<String, Object> valueMap = Maps.newHashMap();

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
		if (id != null) {
			classes.addAll(id.getClasses());
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
	// ---- Getter Setter ToString
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

	public PojoField getId() {
		return id;
	}

	public void setId(PojoField id) {
		this.id = id;
	}

	public List<PojoField> getEntityFields() {
		return pojoFields;
	}

	public void setEntityFields(List<PojoField> pojoFields) {
		this.pojoFields = pojoFields;
	}

	public Map<String, Object> getValueMap() {
		return valueMap;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("PojoClass [packageName=");
		builder.append(packageName);
		builder.append(", superClass=");
		builder.append(superClass);
		builder.append(", interfaces=");
		builder.append(interfaces);
		builder.append(", annotationWrappers=");
		builder.append(annotationWrappers);
		builder.append(", name=");
		builder.append(name);
		builder.append(", id=");
		builder.append(id);
		builder.append(", entityFields=");
		builder.append(pojoFields);
		builder.append(", valueMap=");
		builder.append(valueMap);
		builder.append("]");
		return builder.toString();
	}

}
