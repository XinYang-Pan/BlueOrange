package org.blueo.commons.persistent.entity;

import java.beans.PropertyDescriptor;

import javax.persistence.Entity;
import javax.persistence.Table;

import org.blueo.commons.persistent.jdbc.BlueoJdbcs;
import org.springframework.beans.BeanUtils;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.util.Assert;

import com.google.common.base.Preconditions;

public class AnnotationBased<T> extends BoTable<T> {

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
			if (BlueoJdbcs.isColumn(pd)) {
				Preconditions.checkArgument(!pd.getPropertyType().isPrimitive(), "Primitive field is not supported, and it should never be used in Entity.");
				BoColumn boColumn = this.build(pd);
				if (BlueoJdbcs.isId(pd)) {
					idCol = boColumn;
				} else {
					Preconditions.checkArgument(boColumn.isGeneratedValue(), "Not support none id column to be GeneratedValue!");
					noneIdCols.add(boColumn);
				}
			}
		}
	}
	
	protected PropertyDescriptor getIdPd(Class<T> clazz) {
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
			if (BlueoJdbcs.isColumn(pd) && BlueoJdbcs.isId(pd)) {
				return pd;
			}
		}
		throw new IllegalArgumentException("No ID is found.");
	}

	private BoColumn build(PropertyDescriptor pd) {
		return new BoColumn(BlueoJdbcs.getColumnName(pd), pd, BlueoJdbcs.isGeneratedValue(pd));
	}

}
