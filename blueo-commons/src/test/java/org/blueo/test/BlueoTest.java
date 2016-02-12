package org.blueo.test;

import org.blueo.pojogen.bo.wrapper.clazz.ClassWrapper;

public class BlueoTest {

	public static void main(String[] args) throws Exception {
		ClassWrapper classWrapper = ClassWrapper.of(Object[].class);
		System.out.println(classWrapper);
		System.out.println(classWrapper.getFullName());
		System.out.println(classWrapper.getClassIfPossible());
	}

}
