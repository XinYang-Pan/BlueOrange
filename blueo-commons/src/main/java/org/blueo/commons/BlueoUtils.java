package org.blueo.commons;

import java.lang.reflect.Method;
import java.util.Iterator;

import org.apache.commons.lang3.ArrayUtils;
import org.springframework.util.Assert;

public abstract class BlueoUtils {

	public static boolean isSetMethod(Method method) {
		String methodName = method.getName();
		Class<?>[] parameters = method.getParameterTypes();
		return methodName.startsWith("set") 
				&& ArrayUtils.getLength(parameters) == 1 
				&& (void.class == method.getReturnType()||Void.class == method.getReturnType());
	}

	public static boolean isGetMethod(Method method) {
		String methodName = method.getName();
		Class<?>[] parameters = method.getParameterTypes();
		if (ArrayUtils.getLength(parameters) != 0) {
			return false;
		}
		if (void.class == method.getReturnType() || Void.class == method.getReturnType()) {
			return false;
		}
		if (methodName.startsWith("get")) {
			return true;
		} else if (methodName.startsWith("is")) {
			return boolean.class == method.getReturnType();
		}
		return false;
	}
	
	public static <T> T oneOrNull(Iterable<T> iterable) {
		if (iterable == null) {
			return null;
		}
		// empty
		if (!iterable.iterator().hasNext()) {
			return null;
		}
		return oneNoNull(iterable);
	}
	
	public static <T> T oneNoNull(Iterable<T> iterable) {
		// validate not null
		Assert.notNull(iterable);
		// validate not empty
		Iterator<T> iterator = iterable.iterator();
		Assert.isTrue(iterator.hasNext());
		// Get value
		T t = iterator.next();
		// validate has more than 1 value
		Assert.isTrue(!iterator.hasNext());
		return t;
	}
	
	public static RuntimeException internalError(String message) {
		return new RuntimeException(message);
	}
	
	public static IllegalArgumentException illegalArgument(String message) {
		return new IllegalArgumentException(message);
	}
	
}
