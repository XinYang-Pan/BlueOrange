package org.blueo.example.commons;

import org.blueo.codegen.JdbcGenerator;
import org.blueo.commons.jdbc.EntityWrapper;
import org.blueo.test.object.YzpCodeLog;

public class EntityWrapperExample {

	public static void main(String[] args) {
		EntityWrapper<YzpCodeLog> entityWrapper = EntityWrapper.with(YzpCodeLog.class);
		System.out.println(entityWrapper);
		JdbcGenerator.generateInsertFromEntity(YzpCodeLog.class);
	}
	
}
