package com.aajtech.model.core.impl.java;

import static com.google.common.base.Preconditions.checkNotNull;

import java.lang.reflect.Field;

import com.aajtech.model.core.api.Property;
import com.aajtech.model.core.api.Type;

public class JavaProperty<T, P> implements Property<T, P> {
	private final Field field;

	public JavaProperty(Field field) {
		this.field = checkNotNull(field);
	}

	@Override
	public String getName() {
		return field.getName();
	}

	@SuppressWarnings("unchecked")
	@Override
	public Type<P> getType() {
		return new JavaType<P>((Class<P>) field.getType());
	}

	@SuppressWarnings("unchecked")
	@Override
	public Type<T> getParent() {
		return new JavaType<T>((Class<T>) field.getDeclaringClass());
	}
}
