package org.blueo.pojogen.bo.wrapper.annotation.buildin;

import java.util.List;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import org.blueo.pojogen.bo.PojoField;
import org.blueo.pojogen.bo.wrapper.annotation.FieldAnnotationWrapper;
import org.blueo.pojogen.bo.wrapper.clazz.ClassWrapper;

import com.google.common.collect.Lists;

public class EnumeratedWrapper extends FieldAnnotationWrapper {
	
	private EnumType enumType;
	
	public EnumeratedWrapper(EnumType enumType) {
		super(Enumerated.class);
		this.enumType = enumType;
	}

	@Override
	public String getDisplayString(PojoField pojoField) {
		String extra = "";
		if (enumType != null) {
			extra = String.format("(%s.%s)", EnumType.class.getSimpleName(), enumType.name());
		}
		return String.format("@%s%s", classWrapper.getName(), extra);
	}
	
	@Override
	public List<ClassWrapper> getImports() {
		List<ClassWrapper> classWrappers = Lists.newArrayList();
		classWrappers.addAll(super.getImports());
		classWrappers.add(ClassWrapper.of(EnumType.class));
		return classWrappers;
	}
	
}
