package org.blueo.pojogen.vo.wrapper;

import javax.persistence.Table;

import org.blueo.pojogen.vo.PojoClass;

public class TableWrapper extends AnnotationWrapper{
	
	public TableWrapper() {
		super(Table.class);
	}

	@Override
	public String getDisplayString(Object object) {
		PojoClass pojoClass = (PojoClass)object;
		return String.format("@%s(name = \"%s\")", annotationClass.getSimpleName(), pojoClass.getValueMap().get("tableName"));
	}

}
