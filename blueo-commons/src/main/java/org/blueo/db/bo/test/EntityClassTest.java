package org.blueo.db.bo.test;

import org.blueo.db.bo.EntityClass;
import org.blueo.db.bo.EntityField;
import org.junit.Test;

import com.google.common.collect.Lists;

public class EntityClassTest {
	
	@Test
	public void test() {
		EntityField entityField = new EntityField();
		entityField.setName("id");
		entityField.setColumnName("ID");
		entityField.setType(Long.class);

		EntityClass entityClass = new EntityClass();
		entityClass.setId(null);
		entityClass.setPackageName("org.blueo.db");
		entityClass.setEntityFields(Lists.newArrayList(entityField));
		entityClass.setTableName(null);
		entityClass.setName("Person");
		
		System.out.println(entityClass.generateClassCode());

	}
	
}
