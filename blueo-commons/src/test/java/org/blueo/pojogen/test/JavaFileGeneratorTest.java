package org.blueo.pojogen.test;

import java.io.Serializable;

import org.blueo.pojogen.JavaFileGenerator;
import org.blueo.pojogen.bo.PojoClass;
import org.blueo.pojogen.bo.PojoField;
import org.blueo.pojogen.bo.PojoField.AnnotationType;
import org.blueo.pojogen.bo.wrapper.AnnotationWrapperUtils;
import org.junit.Test;

import com.google.common.collect.Lists;

public class JavaFileGeneratorTest {
	
	@Test
	public void test() throws Exception {
		PojoField pojoField1 = new PojoField();
		pojoField1.setName("name");
		pojoField1.getValueMap().put("columnName", "NAME");
		pojoField1.setType(String.class);
		pojoField1.addAnnotation(AnnotationType.Get, AnnotationWrapperUtils.COLUMN_WRAPPER);
		
		PojoField pojoField = new PojoField();
		pojoField.setName("id");
		pojoField.getValueMap().put("columnName", "ID");
		pojoField.setType(Long.class);
		pojoField.addAnnotation(AnnotationType.Get, AnnotationWrapperUtils.COLUMN_WRAPPER);

		PojoClass pojoClass = new PojoClass();
		pojoClass.setId(null);
		pojoClass.setPackageName("org.blueo.db");
		pojoClass.setEntityFields(Lists.newArrayList(pojoField, pojoField1));
		pojoClass.setName("Person");
		pojoClass.getValueMap().put("tableName", "TBL_PERSON");
		pojoClass.addAnnotationWrapper(AnnotationWrapperUtils.TABLE_WRAPPER);
		pojoClass.setSuperClass(Object.class);
		pojoClass.addInterfaces(Serializable.class, Cloneable.class, Serializable.class);
		
		// test
		JavaFileGenerator javaFileGenerator = new JavaFileGenerator(pojoClass, "./tmp");
		javaFileGenerator.generateClassCode();
	}
	
}
