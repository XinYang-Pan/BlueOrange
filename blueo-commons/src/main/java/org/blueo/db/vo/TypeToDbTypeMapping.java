package org.blueo.db.vo;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;

import org.springframework.util.Assert;

import com.google.common.collect.Maps;

class TypeToDbTypeMapping {

	private static Map<String, Class<?>> sqlTypeStrToJavaType = Maps.newHashMap();
	private static Map<DbType, Class<?>> sqlTypeToJavaType = Maps.newHashMap();

	static {
		// 
		sqlTypeToJavaType.put(DbType.of("numeric", "4"), Integer.class);
		sqlTypeToJavaType.put(DbType.of("numeric", "8"), Long.class);
		// 
		sqlTypeStrToJavaType.put("bigint", Long.class);
		sqlTypeStrToJavaType.put("varchar", String.class);
		sqlTypeStrToJavaType.put("int", Integer.class);
		sqlTypeStrToJavaType.put("long", Long.class);
		sqlTypeStrToJavaType.put("date", Date.class);
		sqlTypeStrToJavaType.put("boolean", Boolean.class);
		sqlTypeStrToJavaType.put("number", BigDecimal.class);
	}

	static SqlType getJavaType(DbType dbType) {
		Assert.notNull(dbType);
		Class<?> javaType = sqlTypeToJavaType.get(dbType);
		if (javaType == null) {
//			javaType = getJavaType(dbType.getType());
		}
		return null;
	}
	
}
