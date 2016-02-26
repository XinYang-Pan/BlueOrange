package org.blueo.commons.persistent.jdbc.util;

import java.beans.PropertyDescriptor;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.blueo.commons.persistent.entity.EntityColumn;
import org.blueo.commons.persistent.entity.EntityTable;
import org.springframework.beans.BeanUtils;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.JdbcUtils;
import org.springframework.util.ReflectionUtils;

public class ColumnRowMapper<T> implements RowMapper<T> {
	private EntityTable<T> entityTable;

	public ColumnRowMapper(EntityTable<T> boTable) {
		this.entityTable = boTable;
	}

	@Override
	public T mapRow(ResultSet rs, int rowNum) throws SQLException {
		T t = BeanUtils.instantiate(entityTable.getParameterizedClass());
		while (rs.next()) {
			List<EntityColumn> allCols = entityTable.getAllCols();
			for (int i = 0; i < allCols.size(); i++) {
				EntityColumn entityColumn = allCols.get(i);
				PropertyDescriptor pd = entityColumn.getPropertyDescriptor();
				Object value = JdbcUtils.getResultSetValue(rs, i, pd.getPropertyType());
				ReflectionUtils.invokeMethod(pd.getWriteMethod(), t, value);
			}
		}
		return t;
	}

	public String getSelectSqlBeforeWhere() {
		List<String> columnNames = EntityTable.getColumnNames(entityTable.getAllCols());
		String columnPart = StringUtils.join(columnNames, ", ");
		return String.format("SELECT %s FROM %s", columnPart, columnPart);
	}

}