package org.blueo.pojogen;

import java.util.List;

public class EntityClass {
	private String packageName;
	private String name;
	private String tableName;
	private EntityField id;
	private List<EntityField> entityFields;
	
	public String getPackageName() {
		return packageName;
	}

	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public EntityField getId() {
		return id;
	}

	public void setId(EntityField id) {
		this.id = id;
	}

	public List<EntityField> getEntityFields() {
		return entityFields;
	}

	public void setEntityFields(List<EntityField> entityFields) {
		this.entityFields = entityFields;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("EntityClass [packageName=");
		builder.append(packageName);
		builder.append(", name=");
		builder.append(name);
		builder.append(", tableName=");
		builder.append(tableName);
		builder.append(", id=");
		builder.append(id);
		builder.append(", entityFields=");
		builder.append(entityFields);
		builder.append("]");
		return builder.toString();
	}

}
