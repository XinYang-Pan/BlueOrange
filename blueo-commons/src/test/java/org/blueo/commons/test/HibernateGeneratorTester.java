package org.blueo.commons.test;

import org.blueo.codegen.JdbcGenerator;
import org.blueo.commons.jdbc.EntityWrapper;
import org.blueo.commons.test.object.YzpCodeLog;
import org.blueo.commons.tostring.ToStringUtils;

public class HibernateGeneratorTester {

	public static void main(String[] args) {
		EntityWrapper<YzpCodeLog> entityForJdbc = new EntityWrapper<YzpCodeLog>(YzpCodeLog.class);
		System.out.println(entityForJdbc);
		System.out.println(ToStringUtils.wellFormat(entityForJdbc));
		JdbcGenerator.generateInsertFromEntity(YzpCodeLog.class);
	}
	
}
