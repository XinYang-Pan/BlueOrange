package org.blueo.example.csv;

import java.util.List;

import org.blueo.commons.tostring.ToStringUtils;
import org.blueo.csv.Parser;
import org.blueo.example.object.Person;

public class CsvExample {
	
	public static void main(String[] args) {
		try {
			String fileLocation = CsvExample.class.getResource("simple_test.csv").getFile();
			System.out.println(CsvExample.class.getResource("simple_test.csv"));
			List<Person> personList = Parser.propertyParser(Person.class, fileLocation);
			System.out.println(ToStringUtils.wellFormat(personList));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
