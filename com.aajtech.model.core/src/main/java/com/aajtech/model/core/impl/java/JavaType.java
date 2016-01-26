package com.aajtech.model.core.impl.java;

import static com.google.common.base.Preconditions.checkNotNull;

import java.lang.reflect.ParameterizedType;
import java.util.Date;

import com.aajtech.model.core.api.Type;

public class JavaType<T> implements Type<T> {
	public static final JavaType<String> STRING = JavaType.of(String.class);
	public static final JavaType<Integer> INTEGER = JavaType.of(Integer.class);
	public static final JavaType<Date> DATE = JavaType.of(Date.class);

	public static <X> JavaType<X> of(Class<X> javaClass) {
		checkNotNull(javaClass);
		return new JavaType<>(javaClass);
	}

	public static <X> JavaType<X> of(TypeLiteral<X> typeLiteral) {
		checkNotNull(typeLiteral);
		// TODO: This method looses generic information
		ParameterizedType parameterizedType = (ParameterizedType) ((ParameterizedType) typeLiteral.getClass()
				.getGenericSuperclass()).getActualTypeArguments()[0];
		@SuppressWarnings("unchecked")
		Class<X> javaClass = (Class<X>) parameterizedType.getRawType();
		return new JavaType<>(javaClass);
	}

	private final Class<T> javaClass;

	JavaType(Class<T> javaClass) {
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
		String name = javaClass.getCanonicalName();
		return name.substring(0, name.lastIndexOf('.'));
	}

	Class<T> getJavaClass() {
		return javaClass;
	}
}
