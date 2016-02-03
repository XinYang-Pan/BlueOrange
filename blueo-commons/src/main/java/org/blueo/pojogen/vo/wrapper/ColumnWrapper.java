package org.blueo.pojogen.vo.wrapper;

import javax.persistence.Column;

import org.blueo.pojogen.vo.EntityField;

public class ColumnWrapper extends AnnotationWrapper<Column, EntityField>{
	
	public ColumnWrapper() {
		super(Column.class);
	}

	@Override
	public String getDisplayString(EntityField entityField) {
		return String.format("@%s(name = \"%s\")", annotationClass.getSimpleName(), entityField.getColumnName());
	}

}
