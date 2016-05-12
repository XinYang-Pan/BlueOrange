package org.blueo.db.java;

import java.lang.reflect.InvocationTargetException;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.blueo.db.config.DbTableConfig;
import org.blueo.db.config.raw.DbTableConfigRawData;
import org.blueo.db.vo.DbColumn;
import org.blueo.db.vo.DbEnum;
import org.blueo.db.vo.DbType;
import org.blueo.db.vo.TypeToJavaTypeMapping;
import org.blueo.db.vo.raw.DbColumnRawData;
import org.blueo.pojogen.bo.wrapper.clazz.ClassWrapper;
import org.springframework.util.Assert;

import com.google.common.base.Preconditions;

public class DataLoaderUtils {

	public static DbTableConfig buildDbTableConfig(DbTableConfigRawData dbTableConfigRawData, Map<String, String> rawType2SqlType) {
		DbTableConfig dbTableConfig = new DbTableConfig();
		dbTableConfig.setTraceable(BooleanUtils.isTrue(BooleanUtils.toBooleanObject(dbTableConfigRawData.getTraceable())));
		dbTableConfig.setTraceType(buildDbType(dbTableConfigRawData.getTraceType(), rawType2SqlType));
		dbTableConfig.setHasId(BooleanUtils.isTrue(BooleanUtils.toBooleanObject(dbTableConfigRawData.getHasId())));
		dbTableConfig.setIdType(buildDbType(dbTableConfigRawData.getIdType(), rawType2SqlType));
		dbTableConfig.setTraceTimeType(buildDbType(dbTableConfigRawData.getTraceTimeType(), rawType2SqlType));
		dbTableConfig.setActiveable(BooleanUtils.isTrue(BooleanUtils.toBooleanObject(dbTableConfigRawData.getActiveable())));
		dbTableConfig.setActiveableType(buildDbType(dbTableConfigRawData.getActiveableType(), rawType2SqlType));
		return dbTableConfig;
	}

	private static DbType buildDbType(String traceType, Map<String, String> rawType2SqlType) {
		DbType dbType = DbType.of(traceType);
		dbType.setSqlType(rawType2SqlType.get(dbType.getRawType()));
		dbType.setJavaType(ClassWrapper.of(TypeToJavaTypeMapping.getJavaType(dbType.getRawType())));
		return dbType;
	}
	
	public static DbColumnRawData buildDbColumnRawData(Map<String, String> valueColumnName) throws IllegalAccessException, InvocationTargetException  {
		DbColumnRawData dbColumnRawData = new DbColumnRawData();
		BeanUtils.populate(dbColumnRawData, valueColumnName);
		return dbColumnRawData;
	}

	public static void overwriteSqlSqlFromDefault(DbColumnRawData dbColumnRawData, Map<String, String> rawType2SqlType) {
		if (dbColumnRawData.getSqlType() == null) {
			dbColumnRawData.setSqlType(rawType2SqlType.get(dbColumnRawData.getType()));
		}
		Preconditions.checkNotNull(dbColumnRawData.getSqlType(), "No sql type is found for %s", dbColumnRawData);
	}

	public static DbColumn buildDbColumn(DbColumnRawData dbColumnRawData, Map<String, DbEnum> name2dbEnumsMap) {
		Assert.notNull(dbColumnRawData);
		DbColumn dbColumn = new DbColumn();
		dbColumn.setName(dbColumnRawData.getName());
		dbColumn.setPk(BooleanUtils.toBoolean(ObjectUtils.firstNonNull(dbColumnRawData.getPk(), "false")));
		dbColumn.setNullable(BooleanUtils.toBoolean(ObjectUtils.firstNonNull(dbColumnRawData.getNullable(), "true")));
		dbColumn.setEnumType(BooleanUtils.toBoolean(ObjectUtils.firstNonNull(dbColumnRawData.getEnumType(), "false")));
		DbType dbType = DbType.of(dbColumnRawData);
		if (dbColumn.isEnumType()) {
			DbEnum dbEnum = name2dbEnumsMap.get(dbType.getRawType());
			dbType.setJavaType(ClassWrapper.of(dbEnum.getPackageName(), dbEnum.getName()));
		} else {
			TypeToJavaTypeMapping.populateJavaType(dbType);
		}
		dbColumn.setDbType(dbType);
		dbColumn.setComment(dbColumnRawData.getComment());
		return dbColumn;
	}
	
}
