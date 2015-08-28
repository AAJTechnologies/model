package com.aajtech.model.core.impl.java;

import static org.junit.Assert.assertEquals;

import java.util.Date;

import org.junit.Test;

import com.aajtech.model.core.api.Value;

public class JavaValueTests {
	@Test
	public void test() throws Exception {
		Value<Person> personValue = Person.TYPE.create();

		Person person = personValue.cast();

		person.setName("a");
		String name = personValue.get(Person.NAME).cast();
		assertEquals("a", name);

		personValue.set(Person.NAME, new JavaValue<>("b")).set(Person.BIRTHDAY, new JavaValue<>(new Date(123)));
		assertEquals("b", person.getName());
		assertEquals(new Date(123), person.getBirthday());

		personValue.set(Person.ADDRESS, Address.TYPE.create());

		personValue.get(Person.ADDRESS).set(Address.STREET, new JavaValue<>("elm"));

		assertEquals("elm", person.getAddress().getStreet());

	}
}
