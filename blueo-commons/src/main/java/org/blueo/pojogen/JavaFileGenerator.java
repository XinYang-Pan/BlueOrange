package org.blueo.pojogen;

import java.util.Formatter;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.ClassUtils;
import org.apache.commons.lang3.StringUtils;
import org.blueo.commons.FormatterWrapper;
import org.blueo.pojogen.vo.PojoClass;
import org.blueo.pojogen.vo.PojoField;
import org.blueo.pojogen.vo.PojoField.AnnotationType;
import org.blueo.pojogen.vo.wrapper.AnnotationWrapper;
import org.springframework.util.CollectionUtils;

public class JavaFileGenerator {
	private PojoClass pojoClass;
	private FormatterWrapper formatterWrapper = new FormatterWrapper(new Formatter(System.out));
	private boolean autoClose;

	public JavaFileGenerator(PojoClass pojoClass) {
		this(pojoClass, new FormatterWrapper(new Formatter(System.out)));
		autoClose = true;
	}

	public JavaFileGenerator(PojoClass pojoClass, FormatterWrapper formatterWrapper) {
		super();
		this.pojoClass = pojoClass;
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
		formatterWrapper.formatln("package %s;", pojoClass.getPackageName());
	}
	
	private void generateImportCode() {
		Set<Class<?>> importClasses = pojoClass.getClasses();
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
		List<AnnotationWrapper> annotationWrappers = pojoClass.getAnnotationWrappers();
		if (annotationWrappers != null) {
			for (AnnotationWrapper annotationWrapper : annotationWrappers) {
				formatterWrapper.formatln(annotationWrapper.getDisplayString(pojoClass));
			}
		}
		// class line
		String extendsString = "";
		if (pojoClass.getSuperClass() != null) {
			extendsString = String.format(" extends %s", pojoClass.getSuperClass().getSimpleName());
		}
		StringBuffer implementsSb = new StringBuffer("");
		if (!CollectionUtils.isEmpty(pojoClass.getInterfaces())) {
			implementsSb.append(" implements ");
			Iterator<Class<?>> iterator = pojoClass.getInterfaces().iterator();
			while (iterator.hasNext()) {
				Class<?> interface_ = (Class<?>) iterator.next();
				implementsSb.append(interface_.getSimpleName());
				if (iterator.hasNext()) {
					implementsSb.append(", ");
				}
			}
		}
		formatterWrapper.formatln("public class %s%s%s {", pojoClass.getName(), extendsString, implementsSb);
	}
	
	private void generateFieldCode() {
		for (PojoField pojoField : pojoClass.getEntityFields()) {
			this.generateFieldCode(pojoField);
		}
	}
	
	private void generateGetSetCode() {
		for (PojoField pojoField : pojoClass.getEntityFields()) {
			this.generateFieldGetCode(pojoField);
			formatterWrapper.formatln();
			this.generateFieldSetCode(pojoField);
			formatterWrapper.formatln();
		}
	}

	private void generateToStringCode() {
		formatterWrapper.formatln(1, "@Override");
		formatterWrapper.formatln(1, "public String toString() {");
		formatterWrapper.formatln(2, "StringBuilder builder = new StringBuilder();");
		boolean first = true;
		for (PojoField pojoField : pojoClass.getEntityFields()) {
			if (first) {
				first = false;
				formatterWrapper.formatln(2, "builder.append(\"%s [%s=\");", pojoClass.getName(), pojoField.getName());
				formatterWrapper.formatln(2, "builder.append(%s);", pojoField.getName());
			} else {
				formatterWrapper.formatln(2, "builder.append(\", %s=\");", pojoField.getName());
				formatterWrapper.formatln(2, "builder.append(%s);", pojoField.getName());
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
	
	public void generateFieldCode(PojoField pojoField) {
		generateAnnotation(pojoField, AnnotationType.Field);
		formatterWrapper.formatln(1, "private %s %s;", pojoField.getType().getSimpleName(), pojoField.getName());
	}
	
	public void generateFieldGetCode(PojoField pojoField) {
		generateAnnotation(pojoField, AnnotationType.Get);
		formatterWrapper.formatln(1, "public %s get%s() {", pojoField.getType().getSimpleName(), StringUtils.capitalize(pojoField.getName()));
		formatterWrapper.formatln(2, "return %s;", pojoField.getName());
		formatterWrapper.formatln(1, "}");
	}
	
	public void generateFieldSetCode(PojoField pojoField) {
		String name = pojoField.getName();
		generateAnnotation(pojoField, AnnotationType.Set);
		formatterWrapper.formatln(1, "public void set%s(%s %s) {", StringUtils.capitalize(name), pojoField.getType().getSimpleName(), name);
		formatterWrapper.formatln(2, "this.%s = %s;", name, name);
		formatterWrapper.formatln(1, "}");
	}

	private void generateAnnotation(PojoField pojoField, AnnotationType annotationType) {
		List<AnnotationWrapper> annotationWrappers = pojoField.getAnnotationWrappers(annotationType);
		for (AnnotationWrapper annotationWrapper : annotationWrappers) {
			formatterWrapper.formatln(1, annotationWrapper.getDisplayString(pojoField));
		}
	}
	
}
