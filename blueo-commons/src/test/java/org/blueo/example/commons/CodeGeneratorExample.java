package org.blueo.example.commons;

import org.blueo.codegen.CodeGenerator;
import org.blueo.db.vo.DbColumn;
import org.blueo.test.object.Person;

public class CodeGeneratorExample {

	public static void main(String[] args) {
		CodeGenerator.generateSetting(DbColumn.class);
		System.out.println("**********************************************");
		CodeGenerator.generateSetting(Person.class, Person.class);
		System.out.println("**********************************************");
		CodeGenerator.generateSetting(Person.class, Person.class, "p1", "p2");
		System.out.println("**********************************************");
	}
	
}
