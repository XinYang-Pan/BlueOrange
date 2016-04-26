package org.blueo.commons.persistent.entity;

import java.beans.PropertyDescriptor;

import javax.persistence.Entity;
import javax.persistence.Table;

import org.springframework.beans.BeanUtils;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.util.Assert;

import com.google.common.base.Preconditions;

public class AnnotationBased<T> extends EntityTable<T> {

	protected void doInit(Class<T> clazz) {
		this.parameterizedClass = clazz;
		// Is a entity
		Assert.notNull(AnnotationUtils.findAnnotation(clazz, Entity.class));
		// Is a table
		Table table = AnnotationUtils.findAnnotation(clazz, Table.class);
		Assert.notNull(table);
		tableName = table.name();
		PropertyDescriptor[] pds = BeanUtils.getPropertyDescriptors(clazz);
		for (PropertyDescriptor pd : pds) {
			// method has Column
			if (EntityUtils.isColumn(pd)) {
				Preconditions.checkArgument(!pd.getPropertyType().isPrimitive(), "Primitive field is not supported, and it should never be used in Entity.");
				EntityColumn entityColumn = this.build(pd);
				if (EntityUtils.isId(pd)) {
					idCol = entityColumn;
					seqName = EntityUtils.getSequenceName(pd);
				} else {
					Preconditions.checkArgument(!entityColumn.isGeneratedValue(), "Not support none id column to be GeneratedValue! ref="+pd);
					noneIdCols.add(entityColumn);
				}
			}
		}
	}

	private EntityColumn build(PropertyDescriptor pd) {
		return new EntityColumn(EntityUtils.getColumnName(pd), pd, EntityUtils.isGeneratedValue(pd));
	}

}
