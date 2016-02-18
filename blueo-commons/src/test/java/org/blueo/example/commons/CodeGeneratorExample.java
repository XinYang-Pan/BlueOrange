package org.blueo.example.commons;

import org.blueo.codegen.set.SetGenerator;
import org.blueo.codegen.setget.SetGetGenerator;
import org.blueo.db.vo.DbColumn;
import org.blueo.example.object.Person;

public class CodeGeneratorExample {

	public static void main(String[] args) {
		SetGenerator.declaredFieldBased(DbColumn.class);
		System.out.println("**********************************************");
		SetGetGenerator.declaredFieldBased(Person.class, Person.class, "p1", "p2");
		System.out.println("**********************************************");
	}
	
}
