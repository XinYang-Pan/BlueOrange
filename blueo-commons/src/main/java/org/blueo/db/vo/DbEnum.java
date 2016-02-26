package org.blueo.db.vo;

import java.util.List;

public class DbEnum {
	private String name;
	private String packageName;
	private List<String> values;

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("DbEnum [name=");
		builder.append(name);
		builder.append(", packageName=");
		builder.append(packageName);
		builder.append(", values=");
		builder.append(values);
		builder.append("]");
		return builder.toString();
	}

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

	public String getPackageName() {
		return packageName;
	}

	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}

}
