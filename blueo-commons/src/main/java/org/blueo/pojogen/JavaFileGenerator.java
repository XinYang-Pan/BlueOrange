package org.blueo.pojogen;

import java.util.Formatter;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.ClassUtils;
import org.apache.commons.lang3.StringUtils;
import org.blueo.commons.FormatterWrapper;
import org.blueo.pojogen.vo.EntityClass;
import org.blueo.pojogen.vo.EntityField;
import org.blueo.pojogen.vo.EntityField.AnnotationType;
import org.blueo.pojogen.vo.wrapper.AnnotationWrapper;
import org.springframework.util.CollectionUtils;

public class JavaFileGenerator {
	private EntityClass entityClass;
	private FormatterWrapper formatterWrapper = new FormatterWrapper(new Formatter(System.out));
	private boolean autoClose;

	public JavaFileGenerator(EntityClass entityClass) {
		this(entityClass, new FormatterWrapper(new Formatter(System.out)));
		autoClose = true;
	}

	public JavaFileGenerator(EntityClass entityClass, FormatterWrapper formatterWrapper) {
		super();
		this.entityClass = entityClass;
		this.formatterWrapper = formatterWrapper;
	}

	public void generateClassCode() {
		this.generatePackageCode();
		formatterWrapper.formatln();
		this.generateImportCode();
		formatterWrapper.formatln();
		this.generateClassDeclarationCode();
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
	
	private void generatePackageCode() {
		formatterWrapper.formatln("package %s;", entityClass.getPackageName());
	}
	
	private void generateImportCode() {
		Set<Class<?>> importClasses = entityClass.getClasses();
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
			formatterWrapper.formatln("import %s;", importClass.getName());
		}
	}
	
	private void generateClassDeclarationCode() {
		// annotation lines
		List<AnnotationWrapper<?, EntityClass>> annotationWrappers = entityClass.getAnnotationWrappers();
		if (annotationWrappers != null) {
			for (AnnotationWrapper<?, EntityClass> annotationWrapper : annotationWrappers) {
				formatterWrapper.formatln(annotationWrapper.getDisplayString(entityClass));
			}
		}
		// class line
		String extendsString = "";
		if (entityClass.getSuperClass() != null) {
			extendsString = String.format(" extends %s", entityClass.getSuperClass().getSimpleName());
		}
		StringBuffer implementsSb = new StringBuffer("");
		if (!CollectionUtils.isEmpty(entityClass.getInterfaces())) {
			implementsSb.append(" implements ");
			Iterator<Class<?>> iterator = entityClass.getInterfaces().iterator();
			while (iterator.hasNext()) {
				Class<?> interface_ = (Class<?>) iterator.next();
				implementsSb.append(interface_.getSimpleName());
				if (iterator.hasNext()) {
					implementsSb.append(", ");
				}
			}
		}
		formatterWrapper.formatln("public class %s%s%s {", entityClass.getName(), extendsString, implementsSb);
	}
	
	private void generateFieldCode() {
		for (EntityField entityField : entityClass.getEntityFields()) {
			this.generateFieldCode(entityField);
		}
	}
	
	private void generateGetSetCode() {
		for (EntityField entityField : entityClass.getEntityFields()) {
			this.generateFieldGetCode(entityField);
			formatterWrapper.formatln();
			this.generateFieldSetCode(entityField);
			formatterWrapper.formatln();
		}
	}

	private void generateToStringCode() {
		formatterWrapper.formatln(1, "@Override");
		formatterWrapper.formatln(1, "public String toString() {");
		formatterWrapper.formatln(2, "StringBuilder builder = new StringBuilder();");
		boolean first = true;
		for (EntityField entityField : entityClass.getEntityFields()) {
			if (first) {
				first = false;
				formatterWrapper.formatln(2, "builder.append(\"%s [%s=\");", entityClass.getName(), entityField.getName());
				formatterWrapper.formatln(2, "builder.append(%s);", entityField.getName());
			} else {
				formatterWrapper.formatln(2, "builder.append(\", %s=\");", entityField.getName());
				formatterWrapper.formatln(2, "builder.append(%s);", entityField.getName());
			}
		}
		formatterWrapper.formatln(2, "builder.append(\"]\");");
		formatterWrapper.formatln(2, "return builder.toString();");
		formatterWrapper.formatln(1, "}");
	}
	
	private void generateEndCode() {
		formatterWrapper.formatln("}");
	}
	
	// --------------------------------------
	// ---- field code generate method
	// --------------------------------------
	
	public void generateFieldCode(EntityField entityField) {
		generateAnnotation(entityField, AnnotationType.Field);
		formatterWrapper.formatln(1, "private %s %s;", entityField.getType().getSimpleName(), entityField.getName());
	}
	
	public void generateFieldGetCode(EntityField entityField) {
		generateAnnotation(entityField, AnnotationType.Get);
		formatterWrapper.formatln(1, "public %s get%s() {", entityField.getType().getSimpleName(), StringUtils.capitalize(entityField.getName()));
		formatterWrapper.formatln(2, "return %s;", entityField.getName());
		formatterWrapper.formatln(1, "}");
	}
	
	public void generateFieldSetCode(EntityField entityField) {
		String name = entityField.getName();
		generateAnnotation(entityField, AnnotationType.Set);
		formatterWrapper.formatln(1, "public void set%s(%s %s) {", StringUtils.capitalize(name), entityField.getType().getSimpleName(), name);
		formatterWrapper.formatln(2, "this.%s = %s;", name, name);
		formatterWrapper.formatln(1, "}");
	}

	private void generateAnnotation(EntityField entityField, AnnotationType annotationType) {
		List<AnnotationWrapper<?, EntityField>> annotationWrappers = entityField.getAnnotationWrappers(annotationType);
		for (AnnotationWrapper<?, EntityField> annotationWrapper : annotationWrappers) {
			formatterWrapper.formatln(1, annotationWrapper.getDisplayString(entityField));
		}
	}
	
}
