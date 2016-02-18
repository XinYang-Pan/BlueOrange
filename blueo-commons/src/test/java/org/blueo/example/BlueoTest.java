package org.blueo.example;

import java.lang.reflect.Field;
import java.util.Arrays;

import org.blueo.example.object.Person;

public class BlueoTest {

	public static void main(String[] args) throws Exception {
		Field[] declaredFields = Person.class.getDeclaredFields();
		for (Field field : declaredFields) {
			System.out.println(field.getName());
		}
		System.out.println(Arrays.toString(declaredFields));
	}

}
