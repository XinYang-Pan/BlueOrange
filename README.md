# BlueOrange
Blue Orange Utilities

## org.blueo.db.DbTool - generate db related artifacts from a predefined excel
1. generate ddl
2. generate po
3. generate dao - TODO

#### Sample Code
```java
org.blueo.example.table.DbToolExample.main(String[])
```
#### Sample Code
```java
URL url = DbToolExample.class.getResource("test.xls");
DbTool dbTool = DbTool.build(url.getPath());
//
dbTool.setPrintToConsole(true);
System.out.println("********************************");
dbTool.generateCreateDdls();
System.out.println("********************************");
dbTool.generatePos();
System.out.println("********************************");
```

## org.blueo.commons.FormatterWrapper
1. Enhancement and wrapper of java.util.Formatter
2. New Feature - add print line support method formatln
3. New Feature - add prefix support
4. New Feature - add message format support

#### Sample Code
```java
org.blueo.codegen.CodeGenerator.generateSetting(Class<?>, String)
```

##org.blueo.commons.tostring.ToStringUtils
#### Sample Code
```java
org.blueo.example.commons.ToStringExample.main(String[])
```
```java
		Person techBoss = new Person("Jack Tech", "male", 1);
		Department tech = new Department();
		tech.setName("tech");
		tech.setBoss(techBoss);
		tech.setEmploys(Lists.newArrayList(techBoss, new Person("Bill Tech", "male", 1)));
		Person salesBoss = new Person("Jack", "male", 1);
		Department sales = new Department();
		sales.setName("sales");
		sales.setBoss(salesBoss);
		sales.setEmploys(Lists.newArrayList(salesBoss, new Person("Bill", "male", 1)));
		// 
		Company company = new Company();
		company.setName("Google");
		company.setDepartments(Lists.newArrayList(sales, tech));
		System.out.println(ToStringUtils.wellFormat(company));
		System.out.println("********************************");
		System.out.println(ToStringUtils.wellFormat(sales));
		System.out.println("********************************");
		System.out.println(ToStringUtils.wellFormat(techBoss));
```
produce
```
Company [name=Google, 
	departments={
		Department [name=sales, boss=Person [name=Jack, sex=male, age=1, staff=false], 
			employs={
				Person [name=Jack, sex=male, age=1, staff=false],
				Person [name=Bill, sex=male, age=1, staff=false]
			}
		],
		Department [name=tech, boss=Person [name=Jack Tech, sex=male, age=1, staff=false], 
			employs={
				Person [name=Jack Tech, sex=male, age=1, staff=false],
				Person [name=Bill Tech, sex=male, age=1, staff=false]
			}
		]
	}
]
********************************
Department [name=sales, boss=Person [name=Jack, sex=male, age=1, staff=false], 
	employs={
		Person [name=Jack, sex=male, age=1, staff=false],
		Person [name=Bill, sex=male, age=1, staff=false]
	}
]
********************************
Person [name=Jack Tech, sex=male, age=1, staff=false]
```



##org.blueo.commons.jdbc.EntityWrapper<T>
#### Sample Code
```java
org.blueo.example.commons.EntityWrapperExample.main(String[])
```

## org.blueo.pojogen.JavaFileGenerator
#### Sample Code
```java
org.blueo.example.pojogen.JavaFileGeneratorExample.main(String[])
```
```java
PojoField pojoField1 = new PojoField();
pojoField1.setName("name");
pojoField1.getValueMap().put("columnName", "NAME");
pojoField1.setType(String.class);
pojoField1.addAnnotationWrapper(AnnotationType.Get, AnnotationWrapperUtils.COLUMN_WRAPPER);

PojoField pojoField = new PojoField();
pojoField.setName("id");
pojoField.getValueMap().put("columnName", "ID");
pojoField.setType(Long.class);
pojoField.addAnnotation(AnnotationType.Get, Id.class);
pojoField.addAnnotation(AnnotationType.Get, GeneratedValue.class);
pojoField.addAnnotationWrapper(AnnotationType.Get, AnnotationWrapperUtils.COLUMN_WRAPPER);

PojoClass pojoClass = new PojoClass();
pojoClass.setPackageName("org.blueo.db");
pojoClass.setEntityFields(Lists.newArrayList(pojoField, pojoField1));
pojoClass.setName("Person");
pojoClass.getValueMap().put("tableName", "TBL_PERSON");
pojoClass.addAnnotation(Entity.class);
pojoClass.addAnnotationWrapper(AnnotationWrapperUtils.TABLE_WRAPPER);
pojoClass.setSuperClass(Object.class);
pojoClass.addInterfaces(Serializable.class, Cloneable.class, Serializable.class);

JavaFileGenerator javaFileGenerator = new JavaFileGenerator(pojoClass);
javaFileGenerator.generateClassCode();
```
produce
```java
package org.blueo.db;

import javax.persistence.GeneratedValue;
import javax.persistence.Table;
import javax.persistence.Entity;
import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;

@Entity
@Table(name = "TBL_PERSON")
@SuppressWarnings("serial")
public class Person extends Object implements Serializable, Cloneable {
	private Long id;
	private String name;

	@Id
	@GeneratedValue
	@Column(name = "ID")
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(name = "NAME")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Person [id=");
		builder.append(id);
		builder.append(", name=");
		builder.append(name);
		builder.append("]");
		return builder.toString();
	}

}
```

## org.blueo.codegen.CodeGenerator
#### Sample Code
```java
org.blueo.example.commons.CodeGeneratorExample.main(String[])
```
```java
CodeGenerator.generateSetting(DbColumn.class);
System.out.println("**********************************************");
CodeGenerator.generateSetting(Person.class, Person.class);
System.out.println("**********************************************");
CodeGenerator.generateSetting(Person.class, Person.class, "p1", "p2");
System.out.println("**********************************************");
```
produce
```
public DbColumn buildDbColumn() {
	DbColumn dbColumn = new DbColumn();
	dbColumn.setNullable(false);
	dbColumn.setPk(false);
	dbColumn.setName(null);
	dbColumn.setSize(null);
	dbColumn.setType(null);
	dbColumn.setComment(null);
	return dbColumn;
}
**********************************************
public Person buildPerson(Person person1) {
	if (person1 == null) {
		return null;
	}
	Person person = new Person();
	person.setSex(person1.getSex());
	person.setAge(person1.getAge());
	person.setStaff(person1.isStaff());
	person.setName(person1.getName());
	return person;
}
**********************************************
public Person buildPerson(Person p2) {
	if (p2 == null) {
		return null;
	}
	Person p1 = new Person();
	p1.setSex(p2.getSex());
	p1.setAge(p2.getAge());
	p1.setStaff(p2.isStaff());
	p1.setName(p2.getName());
	return p1;
}
**********************************************

```

