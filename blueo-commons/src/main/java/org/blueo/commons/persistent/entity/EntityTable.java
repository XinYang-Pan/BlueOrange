package org.blueo.commons.persistent.entity;

import java.util.List;

import org.springframework.util.Assert;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;

public abstract class EntityTable<T> {

	protected String tableName;
	protected Class<T> parameterizedClass;
	protected EntityColumn idCol = null;
	protected List<EntityColumn> noneIdCols = Lists.newArrayList();
	
	protected EntityTable() {
	}
	
	public static <T> EntityTable<T> annotationBased(Class<T> clazz) {
		Preconditions.checkNotNull(clazz);
		// 
		EntityTable<T> entityTable = new AnnotationBased<T>();
		entityTable.doInit(clazz);
		entityTable.validateAfterInit();
		return entityTable;
	}

	public static List<String> getColumnNames(List<EntityColumn> entityColumns) {
		List<String> columnNames = Lists.newArrayList();
		for (EntityColumn pd : entityColumns) {
			columnNames.add(pd.getColumnName());
		}
		return columnNames;
	}

	private void validateAfterInit() {
		//
		Assert.notNull(this.getParameterizedClass());
		Assert.notNull(this.getIdCol());
		Assert.hasText(this.getTableName());
		Assert.notEmpty(this.getNoneIdCols());
	}
	
	public List<EntityColumn> getNoneGenValueCols() {
		if (idCol.isGeneratedValue()) {
			return this.getAllCols();
		} else {
			return this.getNoneIdCols();
		}
	}

	public List<EntityColumn> getAllCols() {
		List<EntityColumn> allCols = Lists.newArrayList();
		allCols.add(idCol);
		allCols.addAll(noneIdCols);
		return allCols;
	}

	protected abstract void doInit(Class<T> clazz);
	
	// -----------------------------
	// ----- Get Set ToString HashCode Equals
	// -----------------------------

	public String getTableName() {
		return tableName;
	}

	public EntityColumn getIdCol() {
		return idCol;
	}

	public List<EntityColumn> getNoneIdCols() {
		return noneIdCols;
	}

	public Class<T> getParameterizedClass() {
		return parameterizedClass;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("BoTable [tableName=");
		builder.append(tableName);
		builder.append(", parameterizedClass=");
		builder.append(parameterizedClass);
		builder.append(", idCol=");
		builder.append(idCol);
		builder.append(", noneIdCols=");
		builder.append(noneIdCols);
		builder.append("]");
		return builder.toString();
	}

}
