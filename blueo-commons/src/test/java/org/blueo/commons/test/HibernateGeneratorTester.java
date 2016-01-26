package org.blueo.commons.test;

import org.blueo.codegen.JdbcGenerator;
import org.blueo.commons.jdbc.EntityForJdbc;
import org.blueo.commons.test.object.YzpCodeLog;

public class HibernateGeneratorTester {

	public static void main(String[] args) {
		EntityForJdbc<YzpCodeLog> entityForJdbc = new EntityForJdbc<YzpCodeLog>(YzpCodeLog.class);
		System.out.println(entityForJdbc);
		JdbcGenerator.generateInsertFromEntity(YzpCodeLog.class);
	}
	
}
