package org.blueo.pojogen.vo.wrapper;

import javax.persistence.Column;

import org.blueo.pojogen.vo.PojoField;

public class ColumnWrapper extends AnnotationWrapper {
	
	public ColumnWrapper() {
		super(Column.class);
	}

	@Override
	public String getDisplayString(Object object) {
		PojoField pojoField = (PojoField)object;
		return String.format("@%s(name = \"%s\")", annotationClass.getSimpleName(), pojoField.getValueMap().get("columnName"));
	}

}
