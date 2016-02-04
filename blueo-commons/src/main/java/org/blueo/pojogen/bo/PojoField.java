package org.blueo.pojogen.bo;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.blueo.pojogen.bo.wrapper.AnnotationWrapper;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;

public class PojoField extends ValueMapObject {
	public enum AnnotationType {
		Field, Get, Set
	}

	private Class<?> type;
	private String name;
	private Map<AnnotationType, List<AnnotationWrapper<PojoField>>> annotationWrapperMap;

	public void addAnnotation(AnnotationType annotationType, AnnotationWrapper<PojoField> annotationWrapper) {
		if (annotationWrapperMap == null) {
			annotationWrapperMap = Maps.newHashMap();
		}
		List<AnnotationWrapper<PojoField>> list = annotationWrapperMap.get(annotationType);
		if (list == null) {
			annotationWrapperMap.put(annotationType, list = Lists.newArrayList());
		}
		list.add(annotationWrapper);
	}

	public List<AnnotationWrapper<PojoField>> getAnnotationWrappers(AnnotationType annotationType) {
		if (annotationWrapperMap == null) {
			return Collections.emptyList();
		}
		List<AnnotationWrapper<PojoField>> list = annotationWrapperMap.get(annotationType);
		if (list == null) {
			return Collections.emptyList();
		}
		return list;
	}

	public Set<Class<?>> getClasses() {
		Set<Class<?>> classes = Sets.newHashSet();
		if (annotationWrapperMap != null) {
			for (List<AnnotationWrapper<PojoField>> wrappers : annotationWrapperMap.values()) {
				for (AnnotationWrapper<PojoField> wrapper : wrappers) {
					classes.add(wrapper.getAnnotationClass());
				}
			}
		}
		classes.add(this.getType());
		return classes;
	}

	// --------------------------------------
	// ---- Getter Setter toString
	// --------------------------------------

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

	public Map<AnnotationType, List<AnnotationWrapper<PojoField>>> getAnnotationWrapperMap() {
		return annotationWrapperMap;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("EntityField [type=");
		builder.append(type);
		builder.append(", name=");
		builder.append(name);
		builder.append(", annotationWrapperMap=");
		builder.append(annotationWrapperMap);
		builder.append(", valueMap=");
		builder.append(valueMap);
		builder.append("]");
		return builder.toString();
	}

}
