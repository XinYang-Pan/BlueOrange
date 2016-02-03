package org.blueo.db.bo.test;

import java.io.Serializable;
import java.util.LinkedHashSet;
import java.util.List;

import org.blueo.pojogen.JavaFileGenerator;
import org.blueo.pojogen.vo.PojoClass;
import org.blueo.pojogen.vo.PojoField;
import org.blueo.pojogen.vo.PojoField.AnnotationType;
import org.blueo.pojogen.vo.wrapper.AnnotationWrapper;
import org.blueo.pojogen.vo.wrapper.AnnotationWrapperUtils;
import org.junit.Test;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

public class EntityClassTest {
	
	@Test
	public void test() {
		PojoField entityField1 = new PojoField();
		entityField1.setName("name");
		entityField1.getValueMap().put("columnName", "NAME");
		entityField1.setType(String.class);
		entityField1.addAnnotation(AnnotationType.Get, AnnotationWrapperUtils.COLUMN_WRAPPER);
		
		PojoField pojoField = new PojoField();
		pojoField.setName("id");
		entityField1.getValueMap().put("columnName", "ID");
		pojoField.setType(Long.class);
		pojoField.addAnnotation(AnnotationType.Get, AnnotationWrapperUtils.COLUMN_WRAPPER);

		PojoClass pojoClass = new PojoClass();
		pojoClass.setId(null);
		pojoClass.setPackageName("org.blueo.db");
		pojoClass.setEntityFields(Lists.newArrayList(pojoField, entityField1));
		pojoClass.setName("Person");
		pojoClass.getValueMap().put("tableName", "TBL_PERSON");
		List<AnnotationWrapper> annotaionWrappers = Lists.newArrayList();
		annotaionWrappers.add(AnnotationWrapperUtils.TABLE_WRAPPER);
		pojoClass.setAnnotationWrappers(annotaionWrappers);
		pojoClass.setSuperClass(Object.class);
		LinkedHashSet<Class<?>> interfaces = Sets.newLinkedHashSet();
		interfaces.add(Serializable.class);
		interfaces.add(Cloneable.class);
		interfaces.add(Serializable.class);
		pojoClass.setInterfaces(interfaces);
		
		JavaFileGenerator javaFileGenerator = new JavaFileGenerator(pojoClass);
		javaFileGenerator.generateClassCode();

	}
	
}
