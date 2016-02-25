package org.blueo.commons.persistent.entity;

import java.beans.PropertyDescriptor;

public class EntityColumn {
	private String columnName;
	private PropertyDescriptor propertyDescriptor;
	private boolean generatedValue;

	public EntityColumn() {
	}

	public EntityColumn(String columnName, PropertyDescriptor propertyDescriptor) {
		super();
		this.columnName = columnName;
		this.propertyDescriptor = propertyDescriptor;
	}

	public EntityColumn(String columnName, PropertyDescriptor propertyDescriptor, boolean generatedValue) {
		super();
		this.columnName = columnName;
		this.propertyDescriptor = propertyDescriptor;
		this.generatedValue = generatedValue;
	}

	public String getColumnName() {
		return columnName;
	}

	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}

	public PropertyDescriptor getPropertyDescriptor() {
		return propertyDescriptor;
	}

	public void setPropertyDescriptor(PropertyDescriptor propertyDescriptor) {
		this.propertyDescriptor = propertyDescriptor;
	}

	public boolean isGeneratedValue() {
		return generatedValue;
	}

	public void setGeneratedValue(boolean generatedValue) {
		this.generatedValue = generatedValue;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("BoColumn [columnName=");
		builder.append(columnName);
		builder.append(", propertyDescriptor=");
		builder.append(propertyDescriptor);
		builder.append(", generatedValue=");
		builder.append(generatedValue);
		builder.append("]");
		return builder.toString();
	}

}
