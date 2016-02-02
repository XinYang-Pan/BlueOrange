package org.blueo.db.bo;

import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.ClassUtils;
import org.blueo.commons.FormatterNb;

import com.google.common.collect.Sets;

public class EntityClass {
	private String packageName;
	private String name;
	private String tableName;
	private EntityField id;
	private List<EntityField> entityFields;
	
	public String generateClassCode() {
		FormatterNb formatterNb = new FormatterNb();
		formatterNb.formatln(this.generatePackageCode());
		formatterNb.formatln();
		formatterNb.formatln(this.generateImportCode());
		formatterNb.formatln(this.generateClassDeclareCode());
		formatterNb.formatln(this.generateFieldCode());
		formatterNb.format(this.generateGetSetCode());
		formatterNb.formatln(this.generateToStringCode());
		formatterNb.formatln();
		formatterNb.formatln(this.generateEndCode());
		return formatterNb.toString();
	}
	
	private String generatePackageCode() {
		return String.format("package %s;", packageName);
	}
	
	private String generateImportCode() {
		Set<Class<?>> importClasses = Sets.newHashSet();
		for (EntityField entityField : entityFields) {
			importClasses.addAll(entityField.getClasses());
		}
		FormatterNb formatterNb = new FormatterNb();
		for (Class<?> importClass : importClasses) {
			if (ClassUtils.isPrimitiveOrWrapper(importClass)) {
				continue;
			}
			if (importClass.isArray()) {
				continue;
			}
			if (importClass.getName().startsWith("java.lang")) {
				continue;
			}
			formatterNb.formatln("import %s;", importClass.getName());
		}
		return formatterNb.toString();
	}
	
	private String generateClassDeclareCode() {
		return String.format("public class %s {", name);
	}
	
	private String generateFieldCode() {
		FormatterNb formatterNb = new FormatterNb();
		for (EntityField entityField : entityFields) {
			formatterNb.formatln(entityField.generateFieldCode());
		}
		return formatterNb.toString();
	}
	
	private String generateGetSetCode() {
		FormatterNb formatterNb = new FormatterNb();
		for (EntityField entityField : entityFields) {
			formatterNb.formatln(entityField.generateGetCode());
			formatterNb.formatln();
			formatterNb.formatln(entityField.generateSetCode());
			formatterNb.formatln();
		}
		return formatterNb.toString();
	}

	private String generateToStringCode() {
		FormatterNb formatterNb = new FormatterNb();
		formatterNb.formatln(1, "@Override");
		formatterNb.formatln(1, "public String toString() {");
		formatterNb.formatln(2, "StringBuilder builder = new StringBuilder();");
		boolean first = true;
		for (EntityField entityField : entityFields) {
			if (first) {
				first = false;
				formatterNb.formatln(2, "builder.append(\"%s [%s=\");", name, entityField.getName());
				formatterNb.formatln(2, "builder.append(%s);", entityField.getName());
			} else {
				formatterNb.formatln(2, "builder.append(\", %s=\");", entityField.getName());
				formatterNb.formatln(2, "builder.append(%s);", entityField.getName());
			}
		}
		formatterNb.formatln(2, "builder.append(\"]\");");
		formatterNb.formatln(2, "return builder.toString();");
		formatterNb.format(1, "}");
		return formatterNb.toString();
	}
	
	private String generateEndCode() {
		return String.format("}%s", System.lineSeparator());
	}
	
	// -------------------------------------------------------
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
