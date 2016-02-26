package org.blueo.commons.persistent.jdbc.util;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import org.blueo.commons.BlueoUtils;
import org.blueo.commons.persistent.entity.EntityColumn;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.jdbc.core.ArgumentTypePreparedStatementSetter;
import org.springframework.jdbc.core.ParameterizedPreparedStatementSetter;
import org.springframework.jdbc.core.StatementCreatorUtils;
import org.springframework.util.ReflectionUtils;

public class ColumnPpss<T> implements ParameterizedPreparedStatementSetter<T> {
	private final List<EntityColumn> columnPds;

	public ColumnPpss(List<EntityColumn> columnPds) {
		this.columnPds = columnPds;
	}

	@Override
	public void setValues(PreparedStatement ps, T t) throws SQLException {
		Object[] args = new Object[columnPds.size()];
		int[] argTypes = new int[columnPds.size()];

		for (int i = 0; i < columnPds.size(); i++) {
			// ArgumentTypePreparedStatementSetter
			PropertyDescriptor pd = columnPds.get(i).getPropertyDescriptor();
			//
			argTypes[i] = StatementCreatorUtils.javaTypeToSqlParameterType(pd.getPropertyType());
			//
			Method readMethod = pd.getReadMethod();
			Object value = ReflectionUtils.invokeMethod(readMethod, t);
			args[i] = getValue(value, readMethod);
		}
		//
		ArgumentTypePreparedStatementSetter stmtSetter = new ArgumentTypePreparedStatementSetter(args, argTypes);
		stmtSetter.setValues(ps);
	}
	
	private static Object getValue(Object value, Method method) {
		Enumerated enumerated = AnnotationUtils.findAnnotation(method, Enumerated.class);
		if (!(value instanceof Enum<?>)) {
			return value;
		}
		Enum<?> enumValue = (Enum<?>) value;
		if (enumerated == null || enumerated.value() == null || enumerated.value() == EnumType.STRING) {
			return enumValue.toString();
		} else if (enumerated.value() == EnumType.ORDINAL) {
			return enumValue.ordinal();
		} else {
			throw BlueoUtils.illegalArgument("never go here.");
		}
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("InsertPss [columnPds=");
		builder.append(columnPds);
		builder.append("]");
		return builder.toString();
	}
}