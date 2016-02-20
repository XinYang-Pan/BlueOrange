package org.blueo.db.vo;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;

import org.springframework.util.Assert;

import com.google.common.base.Preconditions;
import com.google.common.collect.Maps;

class SqlTypeToJavaTypeMapping {

	private static Map<String, Class<?>> sqlTypeStrToJavaType = Maps.newHashMap();
	private static Map<SqlType, Class<?>> sqlTypeToJavaType = Maps.newHashMap();

	static {
		// 
		sqlTypeToJavaType.put(SqlType.of("numeric", "4"), Integer.class);
		sqlTypeToJavaType.put(SqlType.of("numeric", "8"), Long.class);
		// 
		sqlTypeStrToJavaType.put("bigint", Long.class);
		sqlTypeStrToJavaType.put("varchar", String.class);
		sqlTypeStrToJavaType.put("int", Integer.class);
		sqlTypeStrToJavaType.put("long", Long.class);
		sqlTypeStrToJavaType.put("date", Date.class);
		sqlTypeStrToJavaType.put("boolean", Boolean.class);
		sqlTypeStrToJavaType.put("number", BigDecimal.class);
	}

	static Class<?> getJavaType(String sqlType) {
		Assert.notNull(sqlType);
		return Preconditions.checkNotNull(sqlTypeStrToJavaType.get(sqlType.toLowerCase()), String.format("No java class found for %s.", sqlType));
	}

	static Class<?> getJavaType(SqlType sqlType) {
		Assert.notNull(sqlType);
		Class<?> javaType = sqlTypeToJavaType.get(sqlType);
		if (javaType == null) {
			javaType = getJavaType(sqlType.getType());
		}
		return Preconditions.checkNotNull(javaType, String.format("No java class found for %s.", sqlType));
	}
	
}
