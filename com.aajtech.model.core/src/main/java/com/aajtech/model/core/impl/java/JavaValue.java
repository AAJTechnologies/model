package com.aajtech.model.core.impl.java;

import static com.google.common.base.Preconditions.checkNotNull;

import java.util.Objects;

import javax.annotation.Nullable;

import com.aajtech.model.core.api.Type;
import com.aajtech.model.core.api.Value;
import com.aajtech.model.core.impl.BaseValue;

public class JavaValue<T> extends BaseValue<T> implements Value<T> {
	public static <X> JavaValue<X> of(@Nullable X value, JavaType<X> type) {
		checkNotNull(type);
		return new JavaValue<>(value, type);
	}

	public static <X> JavaValue<X> of(JavaType<X> type) {
		checkNotNull(type);
		return new JavaValue<>(null, type);
	}

	public static <X> JavaValue<X> of(X value) {
		checkNotNull(value);
		@SuppressWarnings("unchecked")
		JavaValue<X> javaValue = new JavaValue<>(value, JavaType.of((Class<X>) value.getClass()));
		return javaValue;
	}

	private T value;
	private final JavaType<T> type;

	JavaValue(T value, JavaType<T> type) {
		this.value = value;
		this.type = type;
	}

	@Override
	@Nullable
	public T get() {
		return value;
	}

	@Override
	public void set(@Nullable T value) {
		T oldValue = this.value;
		this.value = value;
		if (!Objects.equals(oldValue, value)) {
			notifyObservers();
		}
	}

	@Override
	public Type<T> getType() {
		return type;
	}
}
