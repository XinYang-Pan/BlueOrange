package test.typetoken;

import org.blueo.commons.BlueoUtils;

import com.google.common.reflect.TypeToken;

public class SuperTest {

	public static <T> void main(String[] args) {
		@SuppressWarnings("serial")
		Class<String> parameterizedClass = BlueoUtils.getParameterizedClass(new TypeToken<String>(Super.class) {});
		System.out.println(parameterizedClass);
		Child child = new Child();
		System.out.println(child.getParameterizedClass());
	}

}
