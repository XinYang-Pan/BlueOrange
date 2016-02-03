package org.blueo.pojogen.vo;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.blueo.pojogen.vo.wrapper.AnnotationWrapper;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;

public class EntityField {
	public enum AnnotationType {Field, Get, Set}
	private Class<?> type;
	private String name;
	private String columnName;
	private Map<AnnotationType, List<AnnotationWrapper<?, EntityField>>> annotationWrapperMap;
	
	public void addAnnotation(AnnotationType annotationType, AnnotationWrapper<?, EntityField> annotationWrapper) {
		if (annotationWrapperMap == null) {
			annotationWrapperMap = Maps.newHashMap();
		}
		List<AnnotationWrapper<?, EntityField>> list = annotationWrapperMap.get(annotationType);
		if (list == null) {
			annotationWrapperMap.put(annotationType, list = Lists.newArrayList());
		}
		list.add(annotationWrapper);
	}
	
	public List<AnnotationWrapper<?, EntityField>> getAnnotationWrappers(AnnotationType annotationType) {
		if (annotationWrapperMap == null) {
			return Collections.emptyList();
		}
		List<AnnotationWrapper<?, EntityField>> list = annotationWrapperMap.get(annotationType);
		if (list == null) {
			return Collections.emptyList();
		}
		return list;
	}
	
	public Set<Class<?>> getClasses() {
		Set<Class<?>> classes = Sets.newHashSet();
		if (annotationWrapperMap != null) {
			for (List<AnnotationWrapper<?, EntityField>> wrappers : annotationWrapperMap.values()) {
				for (AnnotationWrapper<?, EntityField> wrapper : wrappers) {
					classes.add(wrapper.getAnnotationClass());
				}
			}
		}
		classes.add(this.getType());
		return classes;
	}
	
	public Class<?> getType() {
		return type;
	}

	public void setType(Class<?> type) {
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getColumnName() {
		return columnName;
	}

	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}

	public Map<AnnotationType, List<AnnotationWrapper<?, EntityField>>> getAnnotationWrapperMap() {
		return annotationWrapperMap;
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("EntityField [type=");
		builder.append(type);
		builder.append(", name=");
		builder.append(name);
		builder.append(", columnName=");
		builder.append(columnName);
		builder.append(", annotationWrapperMap=");
		builder.append(annotationWrapperMap);
		builder.append("]");
		return builder.toString();
	}

	
}
