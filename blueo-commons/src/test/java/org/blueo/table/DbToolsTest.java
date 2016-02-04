package org.blueo.table;

import java.io.IOException;
import java.util.List;

import jxl.read.biff.BiffException;

import org.blueo.commons.tostring.ToStringUtils;
import org.blueo.db.load.Loader;
import org.blueo.db.load.SqlUtils;
import org.blueo.db.vo.DbColumn;
import org.blueo.db.vo.DbTable;
import org.blueo.pojogen.JavaFileGenerator;
import org.blueo.pojogen.bo.PojoClass;
import org.blueo.pojogen.bo.PojoField;
import org.blueo.pojogen.bo.PojoField.AnnotationType;
import org.blueo.pojogen.bo.wrapper.AnnotationWrapperUtils;

import com.google.common.base.CaseFormat;
import com.google.common.base.Converter;
import com.google.common.collect.Lists;

public class DbToolsTest {
	private static Converter<String, String> COLUMN_NAME_TO_FIELD_NAME = CaseFormat.UPPER_UNDERSCORE.converterTo(CaseFormat.LOWER_CAMEL);
	private static Converter<String, String> TABLE_NAME_TO_CLASS_NAME = CaseFormat.UPPER_UNDERSCORE.converterTo(CaseFormat.UPPER_CAMEL);
	private static String PACKAGE_NAME = "org.blueo.table.po";

	public static List<PojoClass> buildEntityClasses(List<DbTable> dbTables) {
		List<PojoClass> entityClasses = Lists.newArrayList();
		if (dbTables == null) {
			return entityClasses;
		}
		for (DbTable dbTable : dbTables) {
			entityClasses.add(buildEntityClass(dbTable));
		}
		return entityClasses;
	}
	public static PojoClass buildEntityClass(DbTable dbTable) {
		if (dbTable == null) {
			return null;
		}
		List<PojoField> pojoFields = Lists.newArrayList();
		for (DbColumn dbColumn : dbTable.getDbColumns()) {
			pojoFields.add(buildEntityField(dbColumn));
		}
		// 
		PojoClass pojoClass = new PojoClass();
		pojoClass.setId(buildEntityField(dbTable.getPk()));
		pojoClass.setPackageName(PACKAGE_NAME);
		pojoClass.setEntityFields(pojoFields);
		pojoClass.getValueMap().put("tableName", dbTable.getName());
		pojoClass.setName(TABLE_NAME_TO_CLASS_NAME.convert(dbTable.getName()));
		pojoClass.addAnnotationWrapper(AnnotationWrapperUtils.TABLE_WRAPPER);
		return pojoClass;
	}
	
	public static PojoField buildEntityField(DbColumn dbColumn) {
		if (dbColumn == null) {
			return null;
		}
		PojoField pojoField = new PojoField();
		pojoField.setName(COLUMN_NAME_TO_FIELD_NAME.convert(dbColumn.getName()));
		pojoField.setType(SqlUtils.getJavaType(dbColumn.getType()));
		pojoField.getValueMap().put("columnName", dbColumn.getName());
		pojoField.addAnnotation(AnnotationType.Get, AnnotationWrapperUtils.COLUMN_WRAPPER);
		return pojoField;
	}

	public static void main(String[] args) throws BiffException, IOException {
		List<DbTable> dbTables = Loader.loadFromExcel("src/test/java/org/blueo/table/test.xls");
		System.out.println(ToStringUtils.wellFormat(dbTables));
		for (DbTable dbTable : dbTables) {
			JavaFileGenerator javaFileGenerator = new JavaFileGenerator(buildEntityClass(dbTable), "./tmp");
			javaFileGenerator.generateClassCode();
		}
	}
	
}
