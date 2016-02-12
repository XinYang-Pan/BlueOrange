package org.blueo.example.commons;

import org.blueo.commons.tostring.ToStringUtils;
import org.blueo.example.object.Company;
import org.blueo.example.object.Department;
import org.blueo.example.object.Person;

import com.google.common.collect.Lists;

public class ToStringExample {
	
	public static void main(String[] args) {
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
	}
	
}
