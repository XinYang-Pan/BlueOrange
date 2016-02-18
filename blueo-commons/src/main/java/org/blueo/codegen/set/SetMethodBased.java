package org.blueo.codegen.set;

import java.lang.reflect.Method;
import java.util.Formatter;

import org.apache.commons.lang3.ObjectUtils;
import org.blueo.commons.BlueoUtils;
import org.blueo.commons.FormatterWrapper;
import org.springframework.util.StringUtils;

import com.google.common.base.Defaults;

public class SetMethodBased extends SetGenerator {

	public SetMethodBased(Class<?> targetClass, String paramName) {
		this.targetClass = targetClass;
		this.paramName = paramName;
	}

	@Override
	public void generate(Formatter formatter) {
		paramName = ObjectUtils.firstNonNull(paramName, StringUtils.uncapitalize(targetClass.getSimpleName()));
		String clazzName = targetClass.getSimpleName();
		FormatterWrapper formatterWrapper = new FormatterWrapper(formatter);
		formatterWrapper.formatln(0, "public %s build%s() {", clazzName, clazzName);
		formatterWrapper.formatln(1, "%s %s = new %s();", clazzName, paramName, clazzName);
		// 
		Method[] methods = targetClass.getMethods();
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

}
