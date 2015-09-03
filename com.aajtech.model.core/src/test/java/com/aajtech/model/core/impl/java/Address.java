package com.aajtech.model.core.impl.java;

import com.aajtech.model.core.api.ComplexType;
import com.aajtech.model.core.api.Property;

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
