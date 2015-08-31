package com.aajtech.model.core.api;

import java.util.Observable;

import javax.annotation.Nullable;

public interface Value<T> {
	@Nullable
	T get();

	Value<T> set(@Nullable T value);

	<X> Value<T> set(Property<T, X> property, Value<? extends X> value);

	<X> Value<X> get(Property<T, X> property);

	Observable getObservable();

	Type<T> getType();
}
