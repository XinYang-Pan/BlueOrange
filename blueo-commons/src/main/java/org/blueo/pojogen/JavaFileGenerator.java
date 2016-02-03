package org.blueo.pojogen;

import java.util.Set;

import org.apache.commons.lang3.ClassUtils;
import org.blueo.commons.FormatterWrapper;

import com.google.common.collect.Sets;

public class JavaFileGenerator {
	private EntityClass entityClass;
	
	public JavaFileGenerator(EntityClass entityClass) {
		super();
		this.entityClass = entityClass;
	}

	public String generateClassCode() {
		FormatterWrapper formatterNb = new FormatterWrapper();
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
		return String.format("package %s;", entityClass.getPackageName());
	}
	
	private String generateImportCode() {
		Set<Class<?>> importClasses = Sets.newHashSet();
		for (EntityField entityField : entityClass.getEntityFields()) {
			importClasses.addAll(entityField.getClasses());
		}
		FormatterWrapper formatterNb = new FormatterWrapper();
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
		return String.format("public class %s {", entityClass.getName());
	}
	
	private String generateFieldCode() {
		FormatterWrapper formatterNb = new FormatterWrapper();
		for (EntityField entityField : entityClass.getEntityFields()) {
			formatterNb.formatln(entityField.generateFieldCode());
		}
		return formatterNb.toString();
	}
	
	private String generateGetSetCode() {
		FormatterWrapper formatterNb = new FormatterWrapper();
		for (EntityField entityField : entityClass.getEntityFields()) {
			formatterNb.formatln(entityField.generateGetCode());
			formatterNb.formatln();
			formatterNb.formatln(entityField.generateSetCode());
			formatterNb.formatln();
		}
		return formatterNb.toString();
	}

	private String generateToStringCode() {
		FormatterWrapper formatterNb = new FormatterWrapper();
		formatterNb.formatln(1, "@Override");
		formatterNb.formatln(1, "public String toString() {");
		formatterNb.formatln(2, "StringBuilder builder = new StringBuilder();");
		boolean first = true;
		for (EntityField entityField : entityClass.getEntityFields()) {
			if (first) {
				first = false;
				formatterNb.formatln(2, "builder.append(\"%s [%s=\");", entityClass.getName(), entityField.getName());
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
}
