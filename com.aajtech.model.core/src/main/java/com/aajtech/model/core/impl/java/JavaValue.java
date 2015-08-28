package com.aajtech.model.core.impl.java;

import static com.google.common.base.Preconditions.checkNotNull;

import java.util.Objects;

import javax.annotation.Nullable;

import com.aajtech.model.core.api.Property;
import com.aajtech.model.core.api.Type;
import com.aajtech.model.core.api.Value;
import com.aajtech.model.core.impl.BaseValue;

public class JavaValue<T> extends BaseValue<T> {
	private final T value;
	private final JavaType<T> type;

	public JavaValue(@Nullable T value, JavaType<T> type) {
		this.value = value;
		this.type = checkNotNull(type);
	}

	@SuppressWarnings("unchecked")
	public JavaValue(T value) {
		this(checkNotNull(value), new JavaType<T>((Class<T>) value.getClass()));
	}

	@Override
	public T cast() {
		return value;
	}

	@Override
	public <X> Value<T> set(Property<T, X> property, Value<? extends X> value) {
		String name = checkNotNull(property).getName();
		X oldValue = nativeGet(name);
		nativeSet(name, value.cast());
		if (!Objects.equals(oldValue, value.cast())) {
			observable.setChanged();
			observable.notifyObservers();
		}
		return this;
	}

	@SuppressWarnings("unchecked")
	@Override
	public <X> Value<X> get(Property<T, X> property) {
		String name = checkNotNull(property).getName();
		return new JavaValue<X>(nativeGet(name), new JavaType<>((Class<X>) type.field(name).getType()));
	}

	@Override
	public Type<T> getType() {
		return type;
	}

	private <X> void nativeSet(String name, X fieldValue) {
		try {
			type.field(name).set(value, fieldValue);
		} catch (IllegalAccessException | SecurityException e) {
			throw new IllegalArgumentException("Invalid field: " + name, e);
		}
	}

	@SuppressWarnings("unchecked")
	private <X> X nativeGet(String name) {
		try {
			return (X) type.field(name).get(value);
		} catch (IllegalAccessException | SecurityException e) {
			throw new IllegalArgumentException("Invalid field: " + name, e);
		}
	}
}
