package org.blueo.db.vo;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;

import org.blueo.pojogen.bo.wrapper.clazz.ClassWrapper;
import org.springframework.util.Assert;

import com.google.common.base.Preconditions;
import com.google.common.collect.Maps;

public class TypeToJavaTypeMapping {

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

	public static Class<?> getJavaType(String rawType) {
		Assert.notNull(rawType);
		return Preconditions.checkNotNull(typeStrToJavaType.get(rawType), String.format("No java class found for %s.", rawType));
	}

	public static void populateJavaType(DbType dbType) {
		Assert.notNull(dbType);
		Class<?> javaType = typeStrToJavaType.get(dbType.getRawType());
		Preconditions.checkNotNull(javaType, String.format("No java class found for %s.", dbType));
		dbType.setJavaType(ClassWrapper.of(javaType));
	}

}
