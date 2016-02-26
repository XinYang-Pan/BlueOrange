package org.blueo.db.vo;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;

import org.springframework.util.Assert;

import com.google.common.base.Preconditions;
import com.google.common.collect.Maps;

class TypeToJavaTypeMapping {

	private static Map<String, Class<?>> typeStrToJavaType = Maps.newHashMap();

	static {
		// 
		typeStrToJavaType.put("String", String.class);
		typeStrToJavaType.put("Integer", Integer.class);
		typeStrToJavaType.put("Long", Long.class);
		typeStrToJavaType.put("Date", Date.class);
		typeStrToJavaType.put("Boolean", Boolean.class);
		typeStrToJavaType.put("BigDecimal", BigDecimal.class);

	}

	static Class<?> getJavaType(DbType dbType) {
		Assert.notNull(dbType);
		return Preconditions.checkNotNull(typeStrToJavaType.get(dbType.getType()), String.format("No java class found for %s.", dbType));
	}

}
