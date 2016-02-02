package org.blueo.commons.test;

import org.blueo.codegen.JdbcGenerator;
import org.blueo.commons.jdbc.EntityWrapper;
import org.blueo.commons.test.object.YzpCodeLog;

public class EntityWrapperTester {

	public static void main(String[] args) {
		EntityWrapper<YzpCodeLog> entityWrapper = EntityWrapper.with(YzpCodeLog.class);
		System.out.println(entityWrapper);
		JdbcGenerator.generateInsertFromEntity(YzpCodeLog.class);
	}
	
}
