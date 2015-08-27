package com.aajtech.model.core.impl.java;

import static com.google.common.base.Preconditions.checkNotNull;

import java.lang.reflect.Field;
import java.util.Arrays;

import com.aajtech.model.core.api.Property;
import com.aajtech.model.core.api.Type;
import com.aajtech.model.core.api.Value;
import com.google.common.collect.Iterables;

public class JavaType<T> implements Type<T> {
	private final Class<T> javaClass;

	public JavaType(Class<T> javaClass) {
		this.javaClass = checkNotNull(javaClass);
	}

	@Override
	public Class<T> cast() {
		return javaClass;
	}

	@Override
	public String getName() {
		return javaClass.getName();
	}

	@Override
	public String getNamespace() {
		return javaClass.getPackage().getName();
	}

	@Override
	public Iterable<Property<T, ?>> getProperties() {
		return Iterables.transform(Arrays.asList(javaClass.getFields()), (Field field) -> new JavaProperty<>(field));
	}

	@Override
	public <X> Property<T, X> getProperty(String name) {
		return new JavaProperty<>(field(name));
	}

	@Override
	public Value<T> create() {
		try {
			return new JavaValue<T>(javaClass.newInstance(), this);
		} catch (InstantiationException | IllegalAccessException e) {
			throw new IllegalArgumentException("Can not instantiate class: " + javaClass, e);
		}
	}

	Field field(String name) {
		try {
			Field field = javaClass.getDeclaredField(name);
			field.setAccessible(true);
			return field;
		} catch (NoSuchFieldException | SecurityException e) {
			throw new IllegalArgumentException("Invalid field: " + name, e);
		}
	}

}
