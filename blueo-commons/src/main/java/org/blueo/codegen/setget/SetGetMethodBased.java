package org.blueo.codegen.setget;

import java.lang.reflect.Method;
import java.util.Formatter;

import org.apache.commons.lang3.ObjectUtils;
import org.blueo.commons.BlueoUtils;
import org.blueo.commons.FormatterWrapper;
import org.springframework.util.ReflectionUtils;
import org.springframework.util.StringUtils;

import com.google.common.base.Defaults;
import com.google.common.base.Objects;

public class SetGetMethodBased extends SetGetGenerator {

	public SetGetMethodBased(Class<?> setClass, Class<?> getClass, String setParamName, String getParamName) {
		super();
		this.setClass = setClass;
		this.getClass = getClass;
		this.setParamName = setParamName;
		this.getParamName = getParamName;
	}

	@Override
	public void generate(Formatter formatter) {
		getParamName = ObjectUtils.firstNonNull(getParamName, StringUtils.uncapitalize(getClass.getSimpleName()));
		setParamName = ObjectUtils.firstNonNull(setParamName, StringUtils.uncapitalize(setClass.getSimpleName()));
		if (Objects.equal(getParamName, setParamName)) {
			getParamName = getParamName + "1";
		}
		String setClazzName = setClass.getSimpleName();
		String getClazzName = getClass.getSimpleName();
		//
		FormatterWrapper formatterWrapper = new FormatterWrapper(new Formatter(System.out));
		formatterWrapper.formatln(0, "public %s build%s(%s %s) {", setClazzName, setClazzName, getClazzName, getParamName);
		formatterWrapper.formatln(1, "if (%s == null) {", getParamName);
		formatterWrapper.formatln(2, "return null;");
		formatterWrapper.formatln(1, "}");
		formatterWrapper.formatln(1, "%s %s = new %s();", setClazzName, setParamName, setClazzName);
		//
		Method[] methods = setClass.getMethods();
		for (Method method : methods) {
			Class<?>[] parameters = method.getParameterTypes();
			if (BlueoUtils.isSetMethod(method)) {
				Method getOrIsMethod = setToGet(method, getClass);
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

	private Method setToGet(Method setMethod, Class<?> getClass) {
		String setMethodName = setMethod.getName();
		String getMethodName = "get" + setMethodName.substring(3);
		Method getMethod = ReflectionUtils.findMethod(getClass, getMethodName);
		if (getMethod != null) {
			return getMethod;
		}
		String isMethodName = "is" + setMethodName.substring(3);
		Method isMethod = ReflectionUtils.findMethod(getClass, isMethodName);
		if (isMethod != null) {
			return isMethod;
		}
		return null;
	}

}
