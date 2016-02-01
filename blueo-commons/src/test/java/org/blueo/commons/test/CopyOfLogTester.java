package org.blueo.commons.test;

import java.util.List;

import org.blueo.commons.test.object.Person;

import com.google.common.collect.Lists;

public class CopyOfLogTester {
	
	public static String a(String str, char start, char end) {
		str.indexOf(start);
		
		
		return null;
	}
	
	public static void main(String[] args) {
		List<Person> list = Lists.newArrayList();
		list.add(new Person("a name", "male", 1));
		list.add(new Person("b name", "female", 2));
		String str = String.valueOf(list);
		System.out.println(str);
		str = str.replaceAll("\\[", "\t\n[\t\n");
		str = str.replaceAll(", ", ", \t\n");
		str = str.replaceAll("\\]", "\t\n]\t\n");
		System.out.println(str);
	}
	
}
