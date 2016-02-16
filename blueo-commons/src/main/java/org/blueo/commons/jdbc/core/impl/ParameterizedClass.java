package org.blueo.commons.jdbc.core.impl;

import java.lang.reflect.Type;

import com.google.common.reflect.TypeToken;

public class ParameterizedClass<T> {

	protected final Class<T> clazz;

	public ParameterizedClass() {
		clazz = this.getParameterizedClass();
	}

	@SuppressWarnings({ "unchecked", "serial" })
	public Class<T> getParameterizedClass() {
		TypeToken<T> typeToken = new TypeToken<T>(this.getClass()) {
		};
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
