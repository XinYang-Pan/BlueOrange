package org.blueo.db.java;

import java.util.List;
import java.util.Map;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.text.StrSubstitutor;
import org.blueo.commons.persistent.dao.po.activeable.ActiveablePo;
import org.blueo.commons.persistent.dao.po.id.HasId;
import org.blueo.commons.persistent.dao.po.traceable.TraceablePo;
import org.blueo.db.config.DbTableConfig;
import org.blueo.db.config.raw.DbGlobalConfigRawData;
import org.blueo.db.vo.DbColumn;
import org.blueo.db.vo.DbTable;
import org.blueo.db.vo.DbType;
import org.blueo.pojogen.bo.PojoClass;
import org.blueo.pojogen.bo.PojoField;
import org.blueo.pojogen.bo.PojoField.AnnotationType;
import org.blueo.pojogen.bo.wrapper.annotation.AnnotationWrapperUtils;
import org.blueo.pojogen.bo.wrapper.annotation.buildin.EnumeratedWrapper;
import org.blueo.pojogen.bo.wrapper.annotation.buildin.GeneratedValueWrapper;
import org.blueo.pojogen.bo.wrapper.annotation.buildin.SequenceGeneratorWrapper;
import org.blueo.pojogen.bo.wrapper.clazz.ClassWrapper;
import org.springframework.stereotype.Repository;

import com.google.common.base.CaseFormat;
import com.google.common.base.Converter;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

public class PojoBuildUtils {
	private static Converter<String, String> COLUMN_NAME_TO_FIELD_NAME = CaseFormat.UPPER_UNDERSCORE.converterTo(CaseFormat.LOWER_CAMEL);
	private static Converter<String, String> TABLE_NAME_TO_CLASS_NAME = CaseFormat.UPPER_UNDERSCORE.converterTo(CaseFormat.UPPER_CAMEL);

	public static PojoClass buildDaoClass(PojoClass poClass, DbGlobalConfigRawData dbConfig) {
		PojoClass daoClass = new PojoClass();
		daoClass.setPackageName(dbConfig.getDaoPackage());
		daoClass.setName(String.format("%sDao", poClass.getName()));
		daoClass.addAnnotation(Repository.class);
		//
		Map<String, String> valueMap = Maps.newHashMap();
		valueMap.put("poName", poClass.getFullName());
		String superClassString = StrSubstitutor.replace(dbConfig.getDaoSuperclass(), valueMap);
		//
		daoClass.setSuperClass(ClassWrapper.of(superClassString));
		return daoClass;
	}

	public static PojoClass buildEntityClass(DbTable dbTable, DbGlobalConfigRawData dbConfig) {
		if (dbTable == null) {
			return null;
		}
		DbTableConfig dbTableConfig = dbTable.getDbTableConfig();
		//
		List<PojoField> pojoFields = Lists.newArrayList();
		PojoField pk = buildEntityField(dbTable, dbTable.getPk(), dbConfig, true);
		if (pk != null) {
			pojoFields.add(pk);
		}
		for (DbColumn dbColumn : dbTable.getDbColumns()) {
			pojoFields.add(buildEntityField(dbTable, dbColumn, dbConfig, false));
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
		if (dbTableConfig.isTraceable()) {
			pojoClass.addInterfaces(ClassWrapper.of(TraceablePo.class, dbTableConfig.getTraceType().getJavaType().getFullName()));
		}
		if (dbTableConfig.isActiveable()) {
			pojoClass.addInterfaces(ClassWrapper.of(ActiveablePo.class));
		}
		if (dbTableConfig.isHasId()) {
			pojoClass.addInterfaces(ClassWrapper.of(HasId.class, dbTableConfig.getIdType().getJavaType().getFullName()));
		}
		return pojoClass;
	}

	private static PojoField buildEntityField(DbTable dbTable, DbColumn dbColumn, DbGlobalConfigRawData dbConfig, boolean isPk) {
		if (dbColumn == null) {
			return null;
		}
		PojoField pojoField = new PojoField();
		pojoField.setName(COLUMN_NAME_TO_FIELD_NAME.convert(dbColumn.getName()));
		pojoField.getValueMap().put("columnName", dbColumn.getName());
		if (isPk) {
			pojoField.addAnnotation(AnnotationType.Get, Id.class);
			String seq = dbTable.getSeq();
			if (StringUtils.isEmpty(seq)) {
				pojoField.addAnnotation(AnnotationType.Get, GeneratedValue.class);
			} else {
				// 
				Converter<String, String> converter = CaseFormat.UPPER_UNDERSCORE.converterTo(CaseFormat.LOWER_CAMEL);
				String seqGenName = converter.convert(seq);
				// 
				pojoField.addAnnotationWrapper(AnnotationType.Get, new SequenceGeneratorWrapper(seqGenName, seq));
				pojoField.addAnnotationWrapper(AnnotationType.Get, new GeneratedValueWrapper(GenerationType.SEQUENCE, seqGenName));
			}
		}
		pojoField.addAnnotationWrapper(AnnotationType.Get, AnnotationWrapperUtils.COLUMN_WRAPPER);
		DbType dbType = dbColumn.getDbType();
		ClassWrapper javaType = dbType.getJavaType();
		if (dbColumn.isEnumType()) {
			EnumType enumeratedType = null;
			if ("varchar".equalsIgnoreCase(dbType.getSqlType())
					||"varchar2".equalsIgnoreCase(dbType.getSqlType())) {
				enumeratedType = EnumType.STRING;
			}
			pojoField.addAnnotationWrapper(AnnotationType.Get, new EnumeratedWrapper(enumeratedType));
			pojoField.setType(javaType);
		} else {
			pojoField.setType(javaType);
		}
		return pojoField;
	}

}
