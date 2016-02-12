package org.blueo.test;

import java.text.ParseException;
import java.util.List;

import com.google.common.reflect.Invokable;
import com.google.common.reflect.TypeToken;

public class BlueoTest {

	public static void main(String[] args) throws ParseException {
		System.out.println(String.format("fjwoejfweio %s %n1", "haha"));
	}

	@SuppressWarnings({ "serial", "rawtypes" })
	public static void main1(String[] args) throws Exception {
		TypeToken<List<String>> typeToken = new TypeToken<List<String>>() {
		};
		Invokable<List<String>, ?> invokable = typeToken.method(Object.class.getMethod("hashCode"));
		invokable.getReturnType(); // String.class
		System.out.println(invokable.getReturnType());
		System.out.println(typeToken.getType());
		System.out.println(typeToken.getRawType());
		System.out.println(typeToken.isSubtypeOf(new TypeToken<Iterable<String>>() {
		}));

		TypeToken<List> list = TypeToken.of(List.class);
		System.out.println(list.getType());
		System.out.println(list.getRawType());
	}

}
