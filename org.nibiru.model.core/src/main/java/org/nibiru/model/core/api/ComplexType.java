package org.nibiru.model.core.api;

public interface ComplexType<T> extends Type<T> {
	Iterable<Property<T, ?>> getProperties();

	<X> Property<T, X> getProperty(String name);
}
