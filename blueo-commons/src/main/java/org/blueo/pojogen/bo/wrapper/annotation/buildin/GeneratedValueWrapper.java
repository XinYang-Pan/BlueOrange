package org.blueo.pojogen.bo.wrapper.annotation.buildin;

import java.util.List;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;

import org.blueo.pojogen.bo.PojoField;
import org.blueo.pojogen.bo.wrapper.annotation.FieldAnnotationWrapper;
import org.blueo.pojogen.bo.wrapper.clazz.ClassWrapper;

import com.google.common.collect.Lists;

public class GeneratedValueWrapper extends FieldAnnotationWrapper {

	private GenerationType generationType;
	private String name;
	
	public GeneratedValueWrapper(GenerationType generationType, String name) {
		super(GeneratedValue.class);
		this.generationType = generationType;
		this.name = name;
	}

	@Override
	public String getDisplayString(PojoField pojoField) {
		return String.format("@GeneratedValue(strategy=GenerationType.%s, generator=\"%s\")", generationType.name(), name);
	}
	
	@Override
	public List<ClassWrapper> getImports() {
		List<ClassWrapper> classWrappers = Lists.newArrayList();
		classWrappers.addAll(super.getImports());
		classWrappers.add(ClassWrapper.of(GenerationType.class));
		return classWrappers;
	}
	
}
