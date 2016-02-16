package org.blueo.example.pojogen;

import org.blueo.pojogen.JavaFileGenerator;
import org.blueo.pojogen.bo.PojoClass;
import org.blueo.pojogen.bo.wrapper.clazz.ClassWrapper;

import test.dao.impl.AbstractDao;

public class JavaDaoFileGeneratorExample {
	
	public static void main(String[] args) {
		PojoClass pojoClass = new PojoClass();
		pojoClass.setPackageName("test.org.blueo.db");
		pojoClass.setName("PersonDao");
		pojoClass.setSuperClass(ClassWrapper.of(AbstractDao.class, "test.org.blueo.db.Person", Long.class.getName()));
		
//		JavaFileGenerator javaFileGenerator = new JavaFileGenerator(pojoClass, "./tmp");
		JavaFileGenerator javaFileGenerator = new JavaFileGenerator(pojoClass);
		javaFileGenerator.generateClassCode();
	}
	
}
