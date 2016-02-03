package org.blueo.db.bo.test;

import org.blueo.pojogen.EntityClass;
import org.blueo.pojogen.EntityField;
import org.blueo.pojogen.JavaFileGenerator;
import org.junit.Test;

import com.google.common.collect.Lists;

public class EntityClassTest {
	
	@Test
	public void test() {
		EntityField entityField1 = new EntityField();
		entityField1.setName("name");
		entityField1.setColumnName("NAME");
		entityField1.setType(String.class);
		
		EntityField entityField = new EntityField();
		entityField.setName("id");
		entityField.setColumnName("ID");
		entityField.setType(Long.class);

		EntityClass entityClass = new EntityClass();
		entityClass.setId(null);
		entityClass.setPackageName("org.blueo.db");
		entityClass.setEntityFields(Lists.newArrayList(entityField, entityField1));
		entityClass.setTableName(null);
		entityClass.setName("Person");
		
		JavaFileGenerator javaFileGenerator = new JavaFileGenerator(entityClass);
		System.out.println(javaFileGenerator.generateClassCode());

	}
	
}
