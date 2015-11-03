package com.aajtech.model.core.impl.java;

import static com.google.common.base.Preconditions.checkNotNull;

import javax.annotation.Nullable;

import com.aajtech.model.core.api.ComplexValue;
import com.aajtech.model.core.api.Property;
import com.aajtech.model.core.api.Value;
import com.google.common.annotations.GwtIncompatible;
import com.google.common.base.Objects;

@GwtIncompatible("Reflection")
public class JavaComplexValue<T> extends JavaValue<T>implements ComplexValue<T> {
	public static <X> JavaComplexValue<X> of(@Nullable X value, JavaComplexType<X> type) {
		checkNotNull(type);
		return new JavaComplexValue<>(value, type);
	}

	public static <X> JavaComplexValue<X> of(X value) {
		checkNotNull(value);
		@SuppressWarnings("unchecked")
		JavaComplexValue<X> javaValue = new JavaComplexValue<>(value, JavaComplexType.of((Class<X>) value.getClass()));
		return javaValue;
	}

	private final JavaComplexType<T> type;

	private JavaComplexValue(T value, JavaComplexType<T> type) {
		super(value, type);
		this.type = type;
	}

	@Override
	public <X> ComplexValue<T> set(Property<T, X> property, Value<? extends X> value) {
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
		if (!Objects.equal(oldValue, value.get())) {
			notifyObservers();
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
			return JavaValue.of((X) fieldValue, JavaType.of((Class<X>) type.field(name).getType()));
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public <X> ComplexValue<X> getComplex(Property<T, X> property) {
		String name = checkNotNull(property).getName();
		Object fieldValue = nativeGet(name);
		if (fieldValue instanceof ComplexValue) {
			return (ComplexValue<X>) fieldValue;
		} else {
			return JavaComplexValue.of((X) fieldValue, JavaComplexType.of((Class<X>) type.field(name).getType()));
		}
	}

	private <X> void nativeSet(String name, X fieldValue) {
		try {
			type.field(name).set(get(), fieldValue);
		} catch (IllegalAccessException | SecurityException e) {
			throw new IllegalArgumentException("Invalid field: " + name, e);
		}
	}

	@SuppressWarnings("unchecked")
	private <X> X nativeGet(String name) {
		try {
			return (X) type.field(name).get(get());
		} catch (IllegalAccessException | SecurityException e) {
			throw new IllegalArgumentException("Invalid field: " + name, e);
		}
	}
}
