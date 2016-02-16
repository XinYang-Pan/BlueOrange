package test.typetoken;

import java.lang.reflect.Type;

import com.google.common.reflect.TypeToken;

public class Super<T> {
	
	@SuppressWarnings({ "unchecked", "serial" })
	Class<T> getParameterizedClass() {
		TypeToken<T> typeToken = new TypeToken<T>(this.getClass()) {};
		Type type = typeToken.getType();
		// 
		if (type instanceof Class<?>) {
			Class<T> clazz = (Class<T>) type;
			return clazz;
		} else {
			return null;
		}
	}
	
}
