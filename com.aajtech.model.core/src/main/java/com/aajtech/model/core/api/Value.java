package com.aajtech.model.core.api;

import java.util.Observable;

import javax.annotation.Nullable;

public interface Value<T> {
	@Nullable
	T get();

	void set(@Nullable T value);

	Observable getObservable();

	Type<T> getType();
}
