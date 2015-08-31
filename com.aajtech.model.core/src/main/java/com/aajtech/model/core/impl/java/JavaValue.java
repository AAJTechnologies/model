package com.aajtech.model.core.impl.java;

import static com.google.common.base.Preconditions.checkNotNull;

import java.util.Objects;

import javax.annotation.Nullable;

import com.aajtech.model.core.api.Property;
import com.aajtech.model.core.api.Type;
import com.aajtech.model.core.api.Value;
import com.aajtech.model.core.impl.BaseValue;

public class JavaValue<T> extends BaseValue<T> {
	public static <X> JavaValue<X> of(@Nullable X value, JavaType<X> type) {
		checkNotNull(type);
		return new JavaValue<X>(value, type);
	}

	public static <X> JavaValue<X> of(JavaType<X> type) {
		checkNotNull(type);
		return new JavaValue<X>(null, type);
	}

	public static <X> JavaValue<X> of(X value) {
		checkNotNull(value);
		// TODO: immutable values could be cached? set() method should be
		// removed?
		@SuppressWarnings("unchecked")
		JavaValue<X> javaValue = new JavaValue<X>(value, JavaType.of((Class<X>) value.getClass()));
		return javaValue;
	}

	private T value;
	private final JavaType<T> type;

	private JavaValue(T value, JavaType<T> type) {
		this.value = value;
		this.type = type;
	}

	@Override
	@Nullable
	public T get() {
		return value;
	}

	@Override
	public Value<T> set(@Nullable T value) {
		T oldValue = this.value;
		this.value = value;
		if (!Objects.equals(oldValue, value)) {
			observable.setChanged();
			observable.notifyObservers();
		}
		return this;
	}

	@Override
	public <X> Value<T> set(Property<T, X> property, Value<? extends X> value) {
		String name = checkNotNull(property).getName();
		Object fieldValue = nativeGet(name);
		X oldValue;
		if (fieldValue instanceof Value) {
			@SuppressWarnings("unchecked")
			Value<X> fieldValueX = (Value<X>) fieldValue;
			oldValue = fieldValueX.get();
			fieldValueX.set(value.get());
		} else {
			oldValue = nativeGet(name);
			nativeSet(name, value.get());
		}
		if (!Objects.equals(oldValue, value.get())) {
			observable.setChanged();
			observable.notifyObservers();
		}
		return this;
	}

	@SuppressWarnings("unchecked")
	@Override
	public <X> Value<X> get(Property<T, X> property) {
		String name = checkNotNull(property).getName();
		Object fieldValue = nativeGet(name);
		if (fieldValue instanceof Value) {
			return (Value<X>) fieldValue;
		} else {
			return new JavaValue<X>((X) fieldValue, JavaType.of((Class<X>) type.field(name).getType()));
		}
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
