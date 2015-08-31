package com.aajtech.model.core.impl.java;

import static com.google.common.base.Preconditions.checkNotNull;

import java.lang.reflect.Field;

import com.aajtech.model.core.api.Property;
import com.aajtech.model.core.api.Type;

public class JavaProperty<T, P> implements Property<T, P> {
	public static <X, Y> JavaProperty<X, Y> of(Field field) {
		checkNotNull(field);
		return new JavaProperty<>(field);
	}

	private final Field field;

	private JavaProperty(Field field) {
		this.field = checkNotNull(field);
	}

	@Override
	public String getName() {
		return field.getName();
	}

	@SuppressWarnings("unchecked")
	@Override
	public Type<P> getType() {
		return JavaType.of((Class<P>) field.getType());
	}

	@SuppressWarnings("unchecked")
	@Override
	public Type<T> getParent() {
		return JavaType.of((Class<T>) field.getDeclaringClass());
	}
}
