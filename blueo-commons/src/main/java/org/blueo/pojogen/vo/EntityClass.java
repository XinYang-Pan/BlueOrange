package org.blueo.pojogen.vo;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.blueo.pojogen.vo.wrapper.AnnotationWrapper;

import com.google.common.collect.Sets;

public class EntityClass {
	private String packageName;
	private Class<?> superClass;
	private LinkedHashSet<Class<?>> interfaces;
	private List<AnnotationWrapper<?, EntityClass>> annotationWrappers;
	private String name;
	private String tableName;
	private EntityField id;
	private List<EntityField> entityFields;
	
	public Set<Class<?>> getClasses() {
		Set<Class<?>> classes = Sets.newHashSet();
		if (interfaces != null) {
			for (Class<?> interface_ : interfaces) {
				classes.add(interface_);
			}
		}
		if (annotationWrappers != null) {
			for (AnnotationWrapper<?, EntityClass> wrapper : annotationWrappers) {
				classes.add(wrapper.getAnnotationClass());
			}
		}
		if (entityFields != null) {
			for (EntityField entityField : entityFields) {
				classes.addAll(entityField.getClasses());
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

	public List<AnnotationWrapper<?, EntityClass>> getAnnotationWrappers() {
		return annotationWrappers;
	}

	public void setAnnotationWrappers(List<AnnotationWrapper<?, EntityClass>> annotationWrappers) {
		this.annotationWrappers = annotationWrappers;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public EntityField getId() {
		return id;
	}

	public void setId(EntityField id) {
		this.id = id;
	}

	public List<EntityField> getEntityFields() {
		return entityFields;
	}

	public void setEntityFields(List<EntityField> entityFields) {
		this.entityFields = entityFields;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("EntityClass [packageName=");
		builder.append(packageName);
		builder.append(", superClass=");
		builder.append(superClass);
		builder.append(", interfaces=");
		builder.append(interfaces);
		builder.append(", annotationWrappers=");
		builder.append(annotationWrappers);
		builder.append(", name=");
		builder.append(name);
		builder.append(", tableName=");
		builder.append(tableName);
		builder.append(", id=");
		builder.append(id);
		builder.append(", entityFields=");
		builder.append(entityFields);
		builder.append("]");
		return builder.toString();
	}

}
