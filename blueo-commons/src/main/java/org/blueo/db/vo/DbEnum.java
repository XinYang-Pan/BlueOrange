package org.blueo.db.vo;

import java.util.List;

public class DbEnum {
	private String name;
	private List<String> values;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<String> getValues() {
		return values;
	}

	public void setValues(List<String> values) {
		this.values = values;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("DbEnum [name=");
		builder.append(name);
		builder.append(", values=");
		builder.append(values);
		builder.append("]");
		return builder.toString();
	}

}
