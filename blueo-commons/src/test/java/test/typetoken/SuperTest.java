package test.typetoken;

import org.blueo.commons.jdbc.core.impl.ParameterizedClass;

public class SuperTest {

	public static <T> void main(String[] args) {
		ParameterizedClass<String> parameterizedClass = new ParameterizedClass<String>() {};
		System.out.println(parameterizedClass.getParameterizedClass());
		Child child = new Child();
		System.out.println(child.getParameterizedClass());
	}

}
