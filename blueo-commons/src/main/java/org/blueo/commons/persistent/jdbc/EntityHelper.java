package org.blueo.commons.persistent.jdbc;

import java.beans.PropertyDescriptor;
import java.util.List;
import java.util.Map;

import javax.persistence.Entity;
import javax.persistence.Table;

import org.springframework.beans.BeanUtils;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.util.Assert;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;

public class EntityHelper {

	private List<PropertyDescriptor> noneGenValueCols = Lists.newArrayList();
	private List<PropertyDescriptor> noneIdCols = Lists.newArrayList();
	private List<PropertyDescriptor> allCols = Lists.newArrayList();
	private PropertyDescriptor idCol = null;
	private String tableName;
	private Map<String, PropertyDescriptor> columnName2pdMap;
	
	public static EntityHelper init(Class<?> clazz) {
		EntityHelper entityHelper = new EntityHelper();
		entityHelper.doInit(clazz);
		return entityHelper;
	}
	
	private void doInit(Class<?> clazz) {
		// Is a entity
		Assert.notNull(AnnotationUtils.findAnnotation(clazz, Entity.class));
		// Is a table
		Table table = AnnotationUtils.findAnnotation(clazz, Table.class);
		Assert.notNull(table);
		tableName = table.name();
		PropertyDescriptor[] pds = BeanUtils.getPropertyDescriptors(clazz);
		for (PropertyDescriptor pd : pds) {
			// method has Column, but not GeneratedValue.
			if (BlueoJdbcs.isColumn(pd)) {
				Preconditions.checkArgument(!pd.getPropertyType().isPrimitive(), "Primitive field is not supported, and it should never be used in Entity.");
				allCols.add(pd);
				columnName2pdMap.put(BlueoJdbcs.getColumnName(pd), pd);
				if (BlueoJdbcs.isId(pd)) {
					idCol = pd;
				} else {
					noneIdCols.add(pd);
				}
				if (!BlueoJdbcs.isGeneratedValue(pd)) {
					noneGenValueCols.add(pd);
				}
			}
		}
		Assert.notEmpty(noneGenValueCols);
	}
	
	// -----------------------------
	// ----- Get Set ToString HashCode Equals
	// -----------------------------
	
	public List<PropertyDescriptor> getNoneGenValueCols() {
		return noneGenValueCols;
	}

	public List<PropertyDescriptor> getNoneIdCols() {
		return noneIdCols;
	}

	public PropertyDescriptor getIdCol() {
		return idCol;
	}

	public String getTableName() {
		return tableName;
	}

	public List<PropertyDescriptor> getAllCols() {
		return allCols;
	}

	public Map<String, PropertyDescriptor> getColumnName2pdMap() {
		return columnName2pdMap;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("EntityHelper [noneGenValueCols=");
		builder.append(noneGenValueCols);
		builder.append(", noneIdCols=");
		builder.append(noneIdCols);
		builder.append(", allCols=");
		builder.append(allCols);
		builder.append(", idCol=");
		builder.append(idCol);
		builder.append(", tableName=");
		builder.append(tableName);
		builder.append(", columnName2pdMap=");
		builder.append(columnName2pdMap);
		builder.append("]");
		return builder.toString();
	}

}
