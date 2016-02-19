package org.blueo.commons.persistent.jdbc.util;

import java.beans.PropertyDescriptor;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.JdbcUtils;
import org.springframework.util.ReflectionUtils;

public class ColumnRowMapper<T> implements RowMapper<T> {
	private BoTable<T> boTable;

	public ColumnRowMapper(BoTable<T> boTable) {
		this.boTable = boTable;
	}

	@Override
	public T mapRow(ResultSet rs, int rowNum) throws SQLException {
		T t = BeanUtils.instantiate(boTable.getParameterizedClass());
		while (rs.next()) {
			List<BoColumn> allCols = boTable.getAllCols();
			for (int i = 0; i < allCols.size(); i++) {
				BoColumn boColumn = allCols.get(i);
				PropertyDescriptor pd = boColumn.getPropertyDescriptor();
				Object value = JdbcUtils.getResultSetValue(rs, i, pd.getPropertyType());
				ReflectionUtils.invokeMethod(pd.getWriteMethod(), t, value);
			}
		}
		return t;
	}

	public String getSelectSqlBeforeWhere() {
		List<String> columnNames = BoTable.getColumnNames(boTable.getAllCols());
		String columnPart = StringUtils.join(columnNames, ", ");
		return String.format("SELECT %s FROM %s", columnPart, columnPart);
	}

}