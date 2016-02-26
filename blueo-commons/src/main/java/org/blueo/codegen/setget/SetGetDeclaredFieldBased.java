package org.blueo.codegen.setget;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.util.Formatter;

import org.apache.commons.lang3.ObjectUtils;
import org.blueo.commons.FormatterWrapper;
import org.springframework.beans.BeanUtils;
import org.springframework.util.StringUtils;

import com.google.common.base.Defaults;
import com.google.common.base.Objects;

public class SetGetDeclaredFieldBased extends SetGetGenerator {

	public SetGetDeclaredFieldBased(Class<?> setClass, Class<?> getClass, String setParamName, String getParamName) {
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
		Field[] fields = setClass.getDeclaredFields();
		for (Field field : fields) {
			PropertyDescriptor setPd = BeanUtils.getPropertyDescriptor(setClass, field.getName());
			PropertyDescriptor getPd = BeanUtils.getPropertyDescriptor(getClass, field.getName());
			Object value;
			if (getPd == null) {
				value = Defaults.defaultValue(setPd.getPropertyType());
			} else {
				value = String.format("%s.%s()", getParamName, getPd.getReadMethod().getName());
			}
			formatterWrapper.formatln(1, "%s.%s(%s);", setParamName, setPd.getWriteMethod().getName(), value);
		}
		formatterWrapper.formatln(1, "return %s;", setParamName);
		//
		formatterWrapper.formatln("}");
		formatterWrapper.close();
	}

}
