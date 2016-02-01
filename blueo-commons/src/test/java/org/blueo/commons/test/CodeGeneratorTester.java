package org.blueo.commons.test;

import org.blueo.codegen.CodeGenerator;
import org.blueo.commons.test.object.Person;
import org.blueo.db.vo.DbColumn;

public class CodeGeneratorTester {

	public static void main(String[] args) {
		CodeGenerator.generateSetting(DbColumn.class);
		System.out.println("**********************************************");
		CodeGenerator.generateSetting(Person.class, Person.class);
		System.out.println("**********************************************");
		CodeGenerator.generateSetting(Person.class, Person.class, "p1", "p2");
		System.out.println("**********************************************");
	}
	
}
