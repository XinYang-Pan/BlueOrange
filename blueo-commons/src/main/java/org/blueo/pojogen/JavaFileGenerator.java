package org.blueo.pojogen;

import java.io.Serializable;
import java.util.Formatter;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.ClassUtils;
import org.apache.commons.lang3.StringUtils;
import org.blueo.commons.FormatterWrapper;
import org.blueo.pojogen.bo.PojoClass;
import org.blueo.pojogen.bo.PojoField;
import org.blueo.pojogen.bo.PojoField.AnnotationType;
import org.blueo.pojogen.bo.wrapper.annotation.AnnotationWrapper;
import org.blueo.pojogen.bo.wrapper.clazz.ClassWrapper;
import org.springframework.util.CollectionUtils;

public class JavaFileGenerator {
	private final PojoClass pojoClass;
	private final FormatterWrapper formatterWrapper;
	private final boolean autoClose;

	public JavaFileGenerator(PojoClass pojoClass) {
		this(pojoClass, new FormatterWrapper(new Formatter(System.out)), false);
	}

	public JavaFileGenerator(PojoClass pojoClass, String baseFolder) {
		this.pojoClass = pojoClass;
		String filePath = String.format("%s/%s/%s.java", baseFolder, pojoClass.getPackageName().replace('.', '/'), pojoClass.getName());
		this.formatterWrapper = new FormatterWrapper(FormatterWrapper.createFormatter(filePath));
		autoClose = true;
	}

	public JavaFileGenerator(PojoClass pojoClass, FormatterWrapper formatterWrapper) {
		this(pojoClass, formatterWrapper, true);
	}

	public JavaFileGenerator(PojoClass pojoClass, FormatterWrapper formatterWrapper, boolean autoClose) {
		super();
		this.pojoClass = pojoClass;
		this.formatterWrapper = formatterWrapper;
		this.autoClose = autoClose;
	}

	public void generateClassCode() {
		this.generatePackageCode();
		formatterWrapper.formatln();
		this.generateImportCode();
		formatterWrapper.formatln();
		this.generateClassDeclarationCode(); // 
		this.generateSerialVersionCode();
		this.generateFieldCode();
		formatterWrapper.formatln();
		this.generateGetSetCode();
		this.generateToStringCode();
		formatterWrapper.formatln();
		this.generateEndCode();
		if (autoClose) {
			formatterWrapper.close();
		}
	}

	protected void generatePackageCode() {
		formatterWrapper.formatln("package %s;", pojoClass.getPackageName());
	}

	protected void generateImportCode() {
		Set<ClassWrapper> importClasses = pojoClass.getImports();
		for (ClassWrapper classWrapper : importClasses) {
			Class<?> importClass = classWrapper.getClassIfPossible();
			if (importClass != null) {
				if (ClassUtils.isPrimitiveOrWrapper(importClass)) {
					continue;
				}
				if (importClass.isArray()) {
					continue;
				}
			}
			// 
			if (classWrapper.getPackageName().startsWith("java.lang")) {
				continue;
			}
			formatterWrapper.formatln("import %s;", classWrapper.getFullName());
		}
	}

	protected void generateClassDeclarationCode() {
		// annotation lines
		List<AnnotationWrapper<PojoClass>> annotationWrappers = pojoClass.getAnnotationWrappers();
		if (annotationWrappers != null) {
			for (AnnotationWrapper<PojoClass> annotationWrapper : annotationWrappers) {
				formatterWrapper.formatln(annotationWrapper.getDisplayString(pojoClass));
			}
		}
		// Suppress Serial Warnings
		if (pojoClass.getInterfaces() != null) {
			if (pojoClass.getInterfaces().contains(ClassWrapper.of(Serializable.class))) {
				formatterWrapper.formatln("@SuppressWarnings(\"serial\")");
			}
		}
		// class line
		String extendsString = "";
		if (pojoClass.getSuperClass() != null) {
			extendsString = String.format(" extends %s", pojoClass.getSuperClass().getTypedName());
		}
		StringBuffer implementsSb = new StringBuffer("");
		if (!CollectionUtils.isEmpty(pojoClass.getInterfaces())) {
			implementsSb.append(" implements ");
			Iterator<ClassWrapper> iterator = pojoClass.getInterfaces().iterator();
			while (iterator.hasNext()) {
				ClassWrapper interface_ = (ClassWrapper) iterator.next();
				implementsSb.append(interface_.getTypedName());
				if (iterator.hasNext()) {
					implementsSb.append(", ");
				}
			}
		}
		formatterWrapper.formatln("public class %s%s%s {", pojoClass.getName(), extendsString, implementsSb);
	}

	protected void generateSerialVersionCode() {
		if (pojoClass.getInterfaces() == null) {
			return;
		}
//		if (pojoClass.getInterfaces().contains(Serializable.class)) {
//			formatterWrapper.formatln(1, "private static final long serialVersionUID = %sL;", RandomUtils.nextLong(1, Long.MAX_VALUE));
//			formatterWrapper.formatln();
//		}
	}

	protected void generateFieldCode() {
		List<PojoField> entityFields = pojoClass.getEntityFields();
		if (entityFields == null) {
			return;
		}
		for (PojoField pojoField : entityFields) {
			this.generateFieldCode(pojoField);
		}
	}

	protected void generateGetSetCode() {
		List<PojoField> entityFields = pojoClass.getEntityFields();
		if (entityFields == null) {
			return;
		}
		for (PojoField pojoField : entityFields) {
			this.generateFieldGetCode(pojoField);
			formatterWrapper.formatln();
			this.generateFieldSetCode(pojoField);
			formatterWrapper.formatln();
		}
	}

	protected void generateToStringCode() {
		List<PojoField> entityFields = pojoClass.getEntityFields();
		if (entityFields == null) {
			return;
		}
		formatterWrapper.formatln(1, "@Override");
		formatterWrapper.formatln(1, "public String toString() {");
		formatterWrapper.formatln(2, "StringBuilder builder = new StringBuilder();");
		boolean first = true;
		for (PojoField pojoField : entityFields) {
			if (first) {
				first = false;
				formatterWrapper.formatln(2, "builder.append(\"%s [%s=\");", pojoClass.getName(), pojoField.getName());
			} else {
				formatterWrapper.formatln(2, "builder.append(\", %s=\");", pojoField.getName());
			}
			formatterWrapper.formatln(2, "builder.append(%s);", pojoField.getName());
		}
		formatterWrapper.formatln(2, "builder.append(\"]\");");
		formatterWrapper.formatln(2, "return builder.toString();");
		formatterWrapper.formatln(1, "}");
	}

	protected void generateEndCode() {
		formatterWrapper.formatln("}");
	}

	// --------------------------------------
	// ---- field code generate method
	// --------------------------------------

	protected void generateFieldCode(PojoField pojoField) {
		generateAnnotation(pojoField, AnnotationType.Field);
		formatterWrapper.formatln(1, "private %s %s;", pojoField.getType().getName(), pojoField.getName());
	}

	protected void generateFieldGetCode(PojoField pojoField) {
		generateAnnotation(pojoField, AnnotationType.Get);
		formatterWrapper.formatln(1, "public %s get%s() {", pojoField.getType().getName(), StringUtils.capitalize(pojoField.getName()));
		formatterWrapper.formatln(2, "return %s;", pojoField.getName());
		formatterWrapper.formatln(1, "}");
	}

	protected void generateFieldSetCode(PojoField pojoField) {
		String name = pojoField.getName();
		generateAnnotation(pojoField, AnnotationType.Set);
		formatterWrapper.formatln(1, "public void set%s(%s %s) {", StringUtils.capitalize(name), pojoField.getType().getName(), name);
		formatterWrapper.formatln(2, "this.%s = %s;", name, name);
		formatterWrapper.formatln(1, "}");
	}

	protected void generateAnnotation(PojoField pojoField, AnnotationType annotationType) {
		List<AnnotationWrapper<PojoField>> annotationWrappers = pojoField.getAnnotationWrappers(annotationType);
		for (AnnotationWrapper<PojoField> annotationWrapper : annotationWrappers) {
			formatterWrapper.formatln(1, annotationWrapper.getDisplayString(pojoField));
		}
	}

}
