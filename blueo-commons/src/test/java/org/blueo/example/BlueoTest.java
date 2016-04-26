package org.blueo.example;

import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;


public class BlueoTest {

	public static void main(String[] args) throws Exception {
		System.out.println(Object.class.isAssignableFrom(String.class));

		System.out.println(Iterables.getOnlyElement(Lists.newArrayList(), null));
	}

}
