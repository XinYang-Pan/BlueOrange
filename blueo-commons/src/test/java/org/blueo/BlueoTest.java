package org.blueo;

import java.text.MessageFormat;
import java.text.ParseException;
import java.util.Arrays;

public class BlueoTest {
	
	public static void main(String[] args) throws ParseException {
		MessageFormat messageFormat = new MessageFormat("Hi {0} {0}");
		Object[] objects = messageFormat.parse("Hi Sean Sean");
		System.out.println(Arrays.toString(objects));
	}
	
}
