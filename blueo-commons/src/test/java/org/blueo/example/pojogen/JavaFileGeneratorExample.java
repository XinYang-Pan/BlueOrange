package org.blueo.example.pojogen;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.blueo.pojogen.JavaFileGenerator;
import org.blueo.pojogen.bo.PojoClass;
import org.blueo.pojogen.bo.PojoField;
import org.blueo.pojogen.bo.PojoField.AnnotationType;
import org.blueo.pojogen.bo.wrapper.AnnotationWrapperUtils;

import com.google.common.collect.Lists;

public class JavaFileGeneratorExample {
	
	public static void main(String[] args) {
		PojoField pojoField1 = new PojoField();
		pojoField1.setName("name");
		pojoField1.getValueMap().put("columnName", "NAME");
		pojoField1.setType(String.class);
		pojoField1.addAnnotationWrapper(AnnotationType.Get, AnnotationWrapperUtils.COLUMN_WRAPPER);
		
		PojoField pojoField = new PojoField();
		pojoField.setName("id");
		pojoField.getValueMap().put("columnName", "ID");
		pojoField.setType(Long.class);
		pojoField.addAnnotation(AnnotationType.Get, Id.class);
		pojoField.addAnnotation(AnnotationType.Get, GeneratedValue.class);
		pojoField.addAnnotationWrapper(AnnotationType.Get, AnnotationWrapperUtils.COLUMN_WRAPPER);

		PojoClass pojoClass = new PojoClass();
		pojoClass.setPackageName("org.blueo.db");
		pojoClass.setEntityFields(Lists.newArrayList(pojoField, pojoField1));
		pojoClass.setName("Person");
		pojoClass.getValueMap().put("tableName", "TBL_PERSON");
		pojoClass.addAnnotation(Entity.class);
		pojoClass.addAnnotationWrapper(AnnotationWrapperUtils.TABLE_WRAPPER);
		pojoClass.setSuperClass(Object.class);
		pojoClass.addInterfaces(Serializable.class, Cloneable.class, Serializable.class);
		
//		JavaFileGenerator javaFileGenerator = new JavaFileGenerator(pojoClass, "./tmp");
		JavaFileGenerator javaFileGenerator = new JavaFileGenerator(pojoClass);
		javaFileGenerator.generateClassCode();
	}
	
}
