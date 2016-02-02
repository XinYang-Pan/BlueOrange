package org.blueo.db.bo;

import java.util.List;

import javax.persistence.Column;

import org.apache.commons.lang3.StringUtils;
import org.blueo.commons.FormatterNb;

import com.google.common.collect.Lists;

public class EntityField {
	private static final String PREFIX = "\t";
	private Class<?> type;
	private String name;
	private String columnName;
	
	public String generateFieldCode() {
		return String.format("%sprivate %s %s;", PREFIX, type.getSimpleName(), name);
	}
	
	public String generateGetCode() {
		FormatterNb formatterNb = new FormatterNb(PREFIX);
		formatterNb.formatln(1, "@Column(name = \"%s\")", columnName);
		formatterNb.formatln(1, "public %s get%s() {", type.getSimpleName(), StringUtils.capitalize(name));
		formatterNb.formatln(2, "return %s;", name);
		formatterNb.format(1, "}");
		return formatterNb.toString();
	}
	
	public String generateSetCode() {
		FormatterNb formatterNb = new FormatterNb(PREFIX);
		formatterNb.formatln(1, "public void set%s(%s %s) {", StringUtils.capitalize(name), type.getSimpleName(), name);
		formatterNb.formatln(2, "this.%s = %s;", name, name);
		formatterNb.format(1, "}");
		return formatterNb.toString();
	}
	
	public List<Class<?>> getClasses() {
		return Lists.<Class<?>>newArrayList(Column.class, getType());
	}
	
	public Class<?> getType() {
		return type;
	}

	public void setType(Class<?> type) {
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getColumnName() {
		return columnName;
	}

	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("EntityField [type=");
		builder.append(type);
		builder.append(", name=");
		builder.append(name);
		builder.append(", columnName=");
		builder.append(columnName);
		builder.append("]");
		return builder.toString();
	}
	
}
