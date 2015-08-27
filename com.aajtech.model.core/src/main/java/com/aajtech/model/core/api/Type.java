package com.aajtech.model.core.api;

public interface Type<T> {
	Class<T> cast();
	
	String getName();

	String getNamespace();

	Iterable<Property<T, ?>> getProperties();

	<X> Property<T, X> getProperty(String name);

	Value<T> create();
}