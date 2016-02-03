package org.blueo.table;

import java.io.IOException;
import java.util.List;

import jxl.read.biff.BiffException;

import org.blueo.commons.tostring.ToStringUtils;
import org.blueo.db.load.Loader;
import org.blueo.db.load.SqlUtils;
import org.blueo.db.vo.DbColumn;
import org.blueo.db.vo.DbTable;
import org.blueo.pojogen.EntityClass;
import org.blueo.pojogen.EntityField;
import org.blueo.pojogen.JavaFileGenerator;

import com.google.common.base.CaseFormat;
import com.google.common.base.Converter;
import com.google.common.collect.Lists;

public class DbToolsTest {
	private static Converter<String, String> CONVERTER = CaseFormat.UPPER_UNDERSCORE.converterTo(CaseFormat.LOWER_CAMEL);
	private static String PACKAGE_NAME = "org.blueo.table.po";

	public static List<EntityClass> buildEntityClasses(List<DbTable> dbTables) {
		List<EntityClass> entityClasses = Lists.newArrayList();
		if (dbTables == null) {
			return entityClasses;
		}
		for (DbTable dbTable : dbTables) {
			entityClasses.add(buildEntityClass(dbTable));
		}
		return entityClasses;
	}
	public static EntityClass buildEntityClass(DbTable dbTable) {
		if (dbTable == null) {
			return null;
		}
		List<EntityField> entityFields = Lists.newArrayList();
		for (DbColumn dbColumn : dbTable.getDbColumns()) {
			entityFields.add(buildEntityField(dbColumn));
		}
		// 
		EntityClass entityClass = new EntityClass();
		entityClass.setId(buildEntityField(dbTable.getPk()));
		entityClass.setPackageName(PACKAGE_NAME);
		entityClass.setEntityFields(entityFields);
		entityClass.setTableName(dbTable.getName());
		entityClass.setName(CONVERTER.convert(dbTable.getName()));
		return entityClass;
	}
	
	public static EntityField buildEntityField(DbColumn dbColumn) {
		if (dbColumn == null) {
			return null;
		}
		EntityField entityField = new EntityField();
		entityField.setName(CONVERTER.convert(dbColumn.getName()));
		entityField.setColumnName(dbColumn.getName());
		entityField.setType(SqlUtils.getJavaType(dbColumn.getType()));
		return entityField;
	}

	public static void main(String[] args) throws BiffException, IOException {
		List<DbTable> dbTables = Loader.loadFromExcel("src/test/java/org/blueo/table/test.xls");
		System.out.println(ToStringUtils.wellFormat(dbTables));
		for (DbTable dbTable : dbTables) {
			JavaFileGenerator javaFileGenerator = new JavaFileGenerator(buildEntityClass(dbTable));
			System.out.println(javaFileGenerator.generateClassCode());
		}
	}
	
}
