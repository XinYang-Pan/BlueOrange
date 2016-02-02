package org.blueo.codegen;

import java.lang.reflect.Method;

import org.apache.commons.lang3.ObjectUtils;
import org.blueo.commons.BlueoUtils;
import org.springframework.util.ReflectionUtils;
import org.springframework.util.StringUtils;

import com.google.common.base.Defaults;
import com.google.common.base.Objects;

public abstract class CodeGenerator {
	
	public static void generateSetting(Class<?> clazz) {
		generateSetting(clazz, (String)null);
	}
	
	public static void generateSetting(Class<?> clazz, String paramName) {
		paramName = ObjectUtils.firstNonNull(paramName, StringUtils.uncapitalize(clazz.getSimpleName()));
		String clazzName = clazz.getSimpleName();
		System.out.println(String.format("public %s build%s() {", clazzName, clazzName));
		System.out.println(String.format("\t%s %s = new %s();", clazzName, paramName, clazzName));
		// 
		Method[] methods = clazz.getMethods();
		for (Method method : methods) {
			String methodName = method.getName();
			Class<?>[] parameters = method.getParameterTypes();
			if (BlueoUtils.isSetMethod(method)) {
				System.out.println(String.format("\t%s.%s(%s);", paramName, methodName, Defaults.defaultValue(parameters[0])));
			}
		}
		System.out.println(String.format("\treturn %s;", paramName));
		// 
		System.out.println(String.format("}"));
	}

	public static void generateSetting(Class<?> set, Class<?> get) {
		generateSetting(set, get, null, null);
	}
	
	public static void generateSetting(Class<?> set, Class<?> get, String setParamName, String getParamName) {
		Method[] methods = set.getMethods();
		getParamName = ObjectUtils.firstNonNull(getParamName, StringUtils.uncapitalize(get.getSimpleName()));
		setParamName = ObjectUtils.firstNonNull(setParamName, StringUtils.uncapitalize(set.getSimpleName()));
		if (Objects.equal(getParamName, setParamName)) {
			getParamName = getParamName + "1";
		}
		String setClazzName = set.getSimpleName();
		String getClazzName = get.getSimpleName();
		// 
		System.out.println(String.format("public %s build%s(%s %s) {", setClazzName, setClazzName, getClazzName, getParamName));
		
		System.out.println(String.format("\tif (%s == null) {", getParamName));
		System.out.println(String.format("\t\treturn null;"));
		System.out.println(String.format("\t}"));
		
		System.out.println(String.format("\t%s %s = new %s();", setClazzName, setParamName, setClazzName));
		for (Method method : methods) {
			Class<?>[] parameters = method.getParameterTypes();
			if (BlueoUtils.isSetMethod(method)) {
				Method getOrIsMethod = setToGet(method, get);
				Object value;
				if (getOrIsMethod == null) {
					value = Defaults.defaultValue(parameters[0]);
				} else {
					value = String.format("%s.%s()", getParamName, getOrIsMethod.getName());
				}
				System.out.println(String.format("\t%s.%s(%s);", setParamName, method.getName(), value));
			}
		}
		System.out.println(String.format("\treturn %s;", setParamName));
		// 
		System.out.println(String.format("}"));
	}
	
	private static Method setToGet(Method setMethod, Class<?> getClass) {
		String setMethodName = setMethod.getName();
		String getMethodName = "get"+setMethodName.substring(3);
		Method getMethod = ReflectionUtils.findMethod(getClass, getMethodName);
		if (getMethod != null) {
			return getMethod;
		}
		String isMethodName = "is"+setMethodName.substring(3);
		Method isMethod = ReflectionUtils.findMethod(getClass, isMethodName);
		if (isMethod != null) {
			return isMethod;
		}
		return null;
	}
}
