package org.blueo.db.bo.test;

import org.blueo.pojogen.EntityField;
import org.junit.Test;

public class EntityFieldTest {
	
	@Test
	public void test() {
		EntityField entityField = new EntityField();
		entityField.setName("id");
		entityField.setColumnName("ID");
		entityField.setType(Long.class);

		System.out.println(entityField.generateFieldCode());
		System.out.println(entityField.generateGetCode());
		System.out.println(entityField.generateSetCode());
	}
	
}
