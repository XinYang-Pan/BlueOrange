package org.blueo.db.java;

import java.util.List;
import java.util.Map;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.blueo.db.DbConfig;
import org.blueo.db.vo.DbColumn;
import org.blueo.db.vo.DbTable;
import org.blueo.pojogen.bo.PojoClass;
import org.blueo.pojogen.bo.PojoField;
import org.blueo.pojogen.bo.PojoField.AnnotationType;
import org.blueo.pojogen.bo.wrapper.annotation.AnnotationWrapperUtils;
import org.blueo.pojogen.bo.wrapper.clazz.ClassWrapper;
import org.springframework.util.Assert;

import test.dao.impl.AbstractDao;

import com.google.common.base.CaseFormat;
import com.google.common.base.Converter;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

public class PojoBuildUtils {
	private static Converter<String, String> COLUMN_NAME_TO_FIELD_NAME = CaseFormat.UPPER_UNDERSCORE.converterTo(CaseFormat.LOWER_CAMEL);
	private static Converter<String, String> TABLE_NAME_TO_CLASS_NAME = CaseFormat.UPPER_UNDERSCORE.converterTo(CaseFormat.UPPER_CAMEL);
	private static Map<String, Class<?>> sqlTypeToJavaType = Maps.newHashMap();
	
	static {
		sqlTypeToJavaType.put("bigint", Long.class);
		sqlTypeToJavaType.put("varchar", String.class);
		sqlTypeToJavaType.put("int", Integer.class);
	}

	private static Class<?> getJavaType(String sqlType) {
		Assert.notNull(sqlType);
		return sqlTypeToJavaType.get(sqlType.toLowerCase());
	}
	
	public static PojoClass buildDaoClass(PojoClass pojoClass, DbConfig dbConfig) {
		PojoClass daoClass = new PojoClass();
		daoClass.setPackageName(dbConfig.getDaoPackage());
		daoClass.setName(String.format("%sDao", pojoClass.getName()));
		daoClass.setSuperClass(ClassWrapper.of(AbstractDao.class, String.format("%s.%s", pojoClass.getPackageName(), pojoClass.getName()), Long.class.getName()));
		return daoClass;
	}

	public static PojoClass buildEntityClass(DbTable dbTable, DbConfig dbConfig) {
		if (dbTable == null) {
			return null;
		}
		List<PojoField> pojoFields = Lists.newArrayList();
		PojoField pk = buildEntityField(dbTable.getPk(), true);
		if (pk != null) {
			pojoFields.add(pk);
		}
		for (DbColumn dbColumn : dbTable.getDbColumns()) {
			pojoFields.add(buildEntityField(dbColumn, false));
		}
		//
		PojoClass pojoClass = new PojoClass();
		pojoClass.setPackageName(dbConfig.getPoPackage());
		pojoClass.setEntityFields(pojoFields);
		pojoClass.getValueMap().put("tableName", dbTable.getName());
		pojoClass.setName(TABLE_NAME_TO_CLASS_NAME.convert(dbTable.getName()));
		pojoClass.addAnnotation(Entity.class);
		pojoClass.addAnnotationWrapper(AnnotationWrapperUtils.TABLE_WRAPPER);
		String poSuperclass = dbConfig.getPoSuperclass();
		if (poSuperclass != null) {
			pojoClass.setSuperClass(ClassWrapper.of(poSuperclass));
		}
		List<String> poInterfacesInList = dbConfig.getPoInterfacesInList();
		for (String className : poInterfacesInList) {
			pojoClass.addInterfaces(ClassWrapper.of(className));
		}
		return pojoClass;
	}

	private static PojoField buildEntityField(DbColumn dbColumn, boolean isPk) {
		if (dbColumn == null) {
			return null;
		}
		PojoField pojoField = new PojoField();
		pojoField.setName(COLUMN_NAME_TO_FIELD_NAME.convert(dbColumn.getName()));
		pojoField.setType(PojoBuildUtils.getJavaType(dbColumn.getType()));
		pojoField.getValueMap().put("columnName", dbColumn.getName());
		if (isPk) {
			pojoField.addAnnotation(AnnotationType.Get, Id.class);
			pojoField.addAnnotation(AnnotationType.Get, GeneratedValue.class);
		}
		pojoField.addAnnotationWrapper(AnnotationType.Get, AnnotationWrapperUtils.COLUMN_WRAPPER);
		return pojoField;
	}
}
