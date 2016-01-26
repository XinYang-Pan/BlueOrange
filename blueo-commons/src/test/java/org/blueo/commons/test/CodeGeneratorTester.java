package org.blueo.commons.test;

import org.blueo.commons.CodeGenerator;
import org.blueo.commons.Person;

public class CodeGeneratorTester {

	public static void main(String[] args) {
		CodeGenerator.generateSetting(Person.class);
		System.out.println("**********************************************");
		CodeGenerator.generateSetting(Person.class, Person.class);
		System.out.println("**********************************************");
		CodeGenerator.generateSetting(Person.class, Person.class, "p1", "p2");
	}
	
}
