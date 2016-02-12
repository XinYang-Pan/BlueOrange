package org.blueo.example.object;

public class Person {
	private String name;
	private String sex;
	private int age;
	private boolean staff;
	
	public Person() {
	}
	
	public Person(String name, String sex, int age) {
		super();
		this.name = name;
		this.sex = sex;
		this.age = age;
	}

	public Person(String name, String sex, int age, boolean staff) {
		super();
		this.name = name;
		this.sex = sex;
		this.age = age;
		this.staff = staff;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public boolean isStaff() {
		return staff;
	}

	public void setStaff(boolean staff) {
		this.staff = staff;
	}

}
