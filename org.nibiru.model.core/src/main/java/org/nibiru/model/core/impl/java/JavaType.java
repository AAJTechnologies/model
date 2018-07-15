package org.nibiru.model.core.impl.java;

import static com.google.common.base.Preconditions.checkNotNull;

import java.util.Date;
import java.util.List;

import org.nibiru.model.core.api.Type;

public class JavaType<T> implements Type<T> {
	public static final JavaType<String> STRING = JavaType.of(String.class);
	public static final JavaType<Integer> INTEGER = JavaType.of(Integer.class);
	public static final JavaType<Date> DATE = JavaType.of(Date.class);
	public static final JavaType<Boolean> BOOLEAN = JavaType.of(Boolean.class);

	public static <X> JavaType<X> of(Class<X> javaClass) {
		checkNotNull(javaClass);
		return new JavaType<>(javaClass);
	}

	@SuppressWarnings("unchecked")
	public static <X> JavaType<X> ofUnchecked(Class<?> javaClass) {
		checkNotNull(javaClass);
		return new JavaType<X>((Class<X>) javaClass);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static <X> JavaType<List<X>> ofList(Class<X> javaClass) {
		checkNotNull(javaClass);
		return new JavaType<>((Class)List.class);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static <X> JavaType<Iterable<X>> ofIterable(Class<X> javaClass) {
		checkNotNull(javaClass);
		return new JavaType<>((Class)Iterable.class);
	}

	private final Class<T> javaClass;

	protected JavaType(Class<T> javaClass) {
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
	public String getSimpleName() {
		return javaClass.getSimpleName();
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
