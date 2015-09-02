package com.aajtech.model.core.api;

public interface ComplexValue<T> extends Value<T> {
	<X> ComplexValue<T> set(Property<T, X> property, Value<? extends X> value);

	<X> Value<X> get(Property<T, X> property);

	<X> ComplexValue<X> getComplex(Property<T, X> property);
}
