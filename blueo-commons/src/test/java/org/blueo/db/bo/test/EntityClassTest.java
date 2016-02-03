package org.blueo.db.bo.test;

import java.io.Serializable;
import java.util.LinkedHashSet;
import java.util.List;

import org.blueo.pojogen.JavaFileGenerator;
import org.blueo.pojogen.vo.EntityClass;
import org.blueo.pojogen.vo.EntityField;
import org.blueo.pojogen.vo.EntityField.AnnotationType;
import org.blueo.pojogen.vo.wrapper.AnnotationWrapper;
import org.blueo.pojogen.vo.wrapper.AnnotationWrapperUtils;
import org.junit.Test;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

public class EntityClassTest {
	
	@Test
	public void test() {
		EntityField entityField1 = new EntityField();
		entityField1.setName("name");
		entityField1.setColumnName("NAME");
		entityField1.setType(String.class);
		entityField1.addAnnotation(AnnotationType.Get, AnnotationWrapperUtils.COLUMN_WRAPPER);
		
		EntityField entityField = new EntityField();
		entityField.setName("id");
		entityField.setColumnName("ID");
		entityField.setType(Long.class);
		entityField.addAnnotation(AnnotationType.Get, AnnotationWrapperUtils.COLUMN_WRAPPER);

		EntityClass entityClass = new EntityClass();
		entityClass.setId(null);
		entityClass.setPackageName("org.blueo.db");
		entityClass.setEntityFields(Lists.newArrayList(entityField, entityField1));
		entityClass.setTableName(null);
		entityClass.setName("Person");
		entityClass.setTableName("TBL_PERSON");
		List<AnnotationWrapper<?, EntityClass>> annotaionWrappers = Lists.<AnnotationWrapper<?, EntityClass>>newArrayList();
		annotaionWrappers.add(AnnotationWrapperUtils.TABLE_WRAPPER);
		entityClass.setAnnotationWrappers(annotaionWrappers);
		entityClass.setSuperClass(Object.class);
		LinkedHashSet<Class<?>> interfaces = Sets.newLinkedHashSet();
		interfaces.add(Serializable.class);
		interfaces.add(Cloneable.class);
		interfaces.add(Serializable.class);
		entityClass.setInterfaces(interfaces);
		
		JavaFileGenerator javaFileGenerator = new JavaFileGenerator(entityClass);
		javaFileGenerator.generateClassCode();

	}
	
}
