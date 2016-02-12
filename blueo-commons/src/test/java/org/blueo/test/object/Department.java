package org.blueo.test.object;

import java.util.List;

public class Department {
	private String name;
	private Person boss;
	private List<Person> employs;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Person getBoss() {
		return boss;
	}

	public void setBoss(Person boss) {
		this.boss = boss;
	}

	public List<Person> getEmploys() {
		return employs;
	}

	public void setEmploys(List<Person> employs) {
		this.employs = employs;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Department [name=");
		builder.append(name);
		builder.append(", boss=");
		builder.append(boss);
		builder.append(", employs=");
		builder.append(employs);
		builder.append("]");
		return builder.toString();
	}

}
