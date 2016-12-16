package org.nibiru.model.core.impl.java;

import org.nibiru.model.core.api.ComplexType;
import org.nibiru.model.core.api.Property;

public class Address {
	public static final ComplexType<Address> TYPE = JavaComplexType.of(Address.class);
	public static final Property<Address, String> STREET = TYPE.getProperty("street");

	private String street;

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}
}
