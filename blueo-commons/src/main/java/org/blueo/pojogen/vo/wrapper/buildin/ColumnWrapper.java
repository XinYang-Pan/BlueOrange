package org.blueo.pojogen.vo.wrapper.buildin;

import javax.persistence.Column;

import org.blueo.pojogen.vo.PojoField;
import org.blueo.pojogen.vo.wrapper.FieldAnnotationWrapper;
import org.springframework.util.Assert;

public class ColumnWrapper extends FieldAnnotationWrapper {

	public ColumnWrapper() {
		super(Column.class);
	}

	@Override
	public String getDisplayString(PojoField pojoField) {
		Object columnName = pojoField.getValueMap().get("columnName");
		Assert.notNull(columnName);
		return String.format("@%s(name = \"%s\")", annotationClass.getSimpleName(), columnName);
	}

}
