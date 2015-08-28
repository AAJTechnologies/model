package com.aajtech.model.core.impl.java;

import com.aajtech.model.core.api.Property;
import com.aajtech.model.core.api.Type;

public class Address {
	public static final Type<Address> TYPE = new JavaType<>(Address.class);
	public static final Property<Address, String> STREET = TYPE.getProperty("street");

	private String street;

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}
}
