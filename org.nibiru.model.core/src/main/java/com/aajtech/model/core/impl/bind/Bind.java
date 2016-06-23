package com.aajtech.model.core.impl.bind;

import static com.google.common.base.Preconditions.checkNotNull;

import com.aajtech.model.core.api.Value;
import com.aajtech.model.core.impl.java.JavaType;
import com.aajtech.model.core.impl.java.JavaValue;
import com.google.common.base.Function;

public class Bind<T> {
	private final Value<T> value;

	private Bind(Value<T> value) {
		this.value = value;
	}

	public static <X> Bind<X> on(Value<X> value) {
		checkNotNull(value);
		return new Bind<>(value);
	}

	public <X> Bind<X> map(final Function<T, X> converter) {
		checkNotNull(converter);
		final JavaValue<X> targetValue = JavaValue.of(JavaType.ofUnchecked(Object.class));
		value.addObserver(() -> {
			targetValue.set(converter.apply(value.get()));
		});
		return new Bind<>(targetValue);
	}

	public void to(final Value<T> target) {
		checkNotNull(target);
		value.addObserver(new Runnable() {
			@Override
			public void run() {
				target.set(value.get());
			}
		});
	}
}
