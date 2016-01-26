package org.blueo.commons.test;

import org.blueo.codegen.CodeGenerator;
import org.blueo.commons.test.object.Person;

public class CodeGeneratorTester {

	public static void main(String[] args) {
		CodeGenerator.generateSetting(Person.class);
		System.out.println("**********************************************");
		CodeGenerator.generateSetting(Person.class, Person.class);
		System.out.println("**********************************************");
		CodeGenerator.generateSetting(Person.class, Person.class, "p1", "p2");
	}
	
}
