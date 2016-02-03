package org.blueo.pojogen.vo.wrapper;

import javax.persistence.Table;

import org.blueo.pojogen.vo.EntityClass;

public class TableWrapper extends AnnotationWrapper<Table, EntityClass>{
	
	public TableWrapper() {
		super(Table.class);
	}
	
	@Override
	public String getDisplayString(EntityClass entityClass) {
		return String.format("@%s(name = \"%s\")", annotationClass.getSimpleName(), entityClass.getTableName());
	}

}
