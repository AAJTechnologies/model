package com.aajtech.model.core.impl.java;

import static com.google.common.base.Preconditions.checkNotNull;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Date;

import com.aajtech.model.core.api.Property;
import com.aajtech.model.core.api.Type;
import com.google.common.collect.Iterables;

public class JavaType<T> implements Type<T> {
	public static final JavaType<String> STRING = JavaType.of(String.class);
	public static final JavaType<Integer> INTEGER = JavaType.of(Integer.class);
	public static final JavaType<Date> DATE = JavaType.of(Date.class);

	public static <X> JavaType<X> of(Class<X> javaClass) {
		checkNotNull(javaClass);
		// TODO: Implement a type cache?
		return new JavaType<>(javaClass);
	}

	private final Class<T> javaClass;

	private JavaType(Class<T> javaClass) {
		this.javaClass = javaClass;
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
		return Iterables.transform(Arrays.asList(javaClass.getFields()), (Field field) -> JavaProperty.of(field));
	}

	@Override
	public <X> Property<T, X> getProperty(String name) {
		return JavaProperty.of(field(name));
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
