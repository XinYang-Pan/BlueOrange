package org.blueo.commons.persistent.jdbc.util;

import java.util.List;

import org.springframework.util.Assert;

import com.google.common.collect.Lists;

public abstract class BoTable<T> {

	protected String tableName;
	protected Class<T> parameterizedClass;
	protected BoColumn idCol = null;
	protected List<BoColumn> noneIdCols = Lists.newArrayList();

	public static <T> BoTable<T> annotationBased(Class<T> clazz) {
		BoTable<T> boTable = new AnnotationBased<T>();
		boTable.doInit(clazz);
		//
		Assert.notNull(boTable.getIdCol());
		Assert.hasText(boTable.getTableName());
		Assert.notEmpty(boTable.getNoneIdCols());
		return boTable;
	}

	public static List<String> getColumnNames(List<BoColumn> boColumns) {
		List<String> columnNames = Lists.newArrayList();
		for (BoColumn pd : boColumns) {
			columnNames.add(pd.getColumnName());
		}
		return columnNames;
	}

	public List<BoColumn> getNoneGenValueCols() {
		if (idCol.isGeneratedValue()) {
			return this.getAllCols();
		} else {
			return this.getNoneIdCols();
		}
	}

	public List<BoColumn> getAllCols() {
		List<BoColumn> allCols = Lists.newArrayList();
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

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public BoColumn getIdCol() {
		return idCol;
	}

	public void setIdCol(BoColumn idCol) {
		this.idCol = idCol;
	}

	public List<BoColumn> getNoneIdCols() {
		return noneIdCols;
	}

	public void setNoneIdCols(List<BoColumn> noneIdCols) {
		this.noneIdCols = noneIdCols;
	}

	public Class<T> getParameterizedClass() {
		return parameterizedClass;
	}

	public void setParameterizedClass(Class<T> parameterizedClass) {
		this.parameterizedClass = parameterizedClass;
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
