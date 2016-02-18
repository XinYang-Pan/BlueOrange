package org.blueo.codegen.set;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.util.Formatter;

import org.apache.commons.lang3.ObjectUtils;
import org.blueo.commons.FormatterWrapper;
import org.springframework.beans.BeanUtils;
import org.springframework.util.StringUtils;

import com.google.common.base.Defaults;

public class SetDeclaredFieldBased extends SetGenerator {

	public SetDeclaredFieldBased(Class<?> targetClass, String paramName) {
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
		Field[] fields = targetClass.getDeclaredFields();
		for (Field field : fields) {
			PropertyDescriptor pd = BeanUtils.getPropertyDescriptor(targetClass, field.getName());
			formatterWrapper.formatln(1, "%s.%s(%s);", paramName, pd.getWriteMethod().getName(), Defaults.defaultValue(pd.getPropertyType()));
		}
		formatterWrapper.formatln(1, "return %s;", paramName);
		// 
		formatterWrapper.formatln("}");
		formatterWrapper.close();
	}

}
