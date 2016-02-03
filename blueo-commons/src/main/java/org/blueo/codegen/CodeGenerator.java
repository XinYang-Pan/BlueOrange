package org.blueo.codegen;

import java.lang.reflect.Method;
import java.util.Formatter;

import org.apache.commons.lang3.ObjectUtils;
import org.blueo.commons.BlueoUtils;
import org.blueo.commons.FormatterWrapper;
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
		FormatterWrapper formatterWrapper = new FormatterWrapper(new Formatter(System.out));
		formatterWrapper.formatln(0, "public %s build%s() {", clazzName, clazzName);
		formatterWrapper.formatln(1, "%s %s = new %s();", clazzName, paramName, clazzName);
		// 
		Method[] methods = clazz.getMethods();
		for (Method method : methods) {
			String methodName = method.getName();
			Class<?>[] parameters = method.getParameterTypes();
			if (BlueoUtils.isSetMethod(method)) {
				formatterWrapper.formatln(1, "%s.%s(%s);", paramName, methodName, Defaults.defaultValue(parameters[0]));
			}
		}
		formatterWrapper.formatln(1, "return %s;", paramName);
		// 
		formatterWrapper.formatln("}");
		formatterWrapper.close();
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
		FormatterWrapper formatterWrapper = new FormatterWrapper(new Formatter(System.out));
		formatterWrapper.formatln(0, "public %s build%s(%s %s) {", setClazzName, setClazzName, getClazzName, getParamName);
		formatterWrapper.formatln(1, "if (%s == null) {", getParamName);
		formatterWrapper.formatln(2, "return null;");
		formatterWrapper.formatln(1, "}");
		formatterWrapper.formatln(1, "%s %s = new %s();", setClazzName, setParamName, setClazzName);
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
				formatterWrapper.formatln(1, "%s.%s(%s);", setParamName, method.getName(), value);
			}
		}
		formatterWrapper.formatln(1, "return %s;", setParamName);
		// 
		formatterWrapper.formatln("}");
		formatterWrapper.close();
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
