package org.nibiru.model.core.impl.java;

import java.util.Date;

import org.nibiru.model.core.api.ComplexType;
import org.nibiru.model.core.api.Property;
import org.nibiru.model.core.api.Value;

public class Person {
	public static final ComplexType<Person> TYPE = JavaComplexType.of(Person.class);
	public static final Property<Person, String> NAME = TYPE.getProperty("name");
	public static final Property<Person, Date> BIRTHDAY = TYPE.getProperty("birthday");
	public static final Property<Person, Address> ADDRESS = TYPE.getProperty("address");

	private String name;
	private Address address;
	private Person father;
	public final Value<Date> birthday = JavaValue.of(JavaType.DATE);

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	public Person getFather() {
		return father;
	}

	public void setFather(Person father) {
		this.father = father;
	}

	public Date getBirthday() {
		return birthday.get();
	}

	public void setBirthday(Date birthday) {
		this.birthday.set(birthday);
	}
}
