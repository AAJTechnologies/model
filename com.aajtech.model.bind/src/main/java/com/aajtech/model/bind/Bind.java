package com.aajtech.model.bind;

import static com.google.common.base.Preconditions.checkNotNull;

import java.util.Observable;
import java.util.Observer;

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

	public <X> Bind<X> map(Function<T, X> converter) {
		checkNotNull(converter);
		@SuppressWarnings("unchecked")
		JavaValue<X> targetValue = JavaValue.of(JavaType.of((Class<X>) Object.class));
		this.value.getObservable().addObserver(new Observer() {
			@Override
			public void update(Observable o, Object arg) {
				targetValue.set(converter.apply(value.get()));
			}
		});
		return new Bind<>(targetValue);
	}

	public void to(Value<T> target) {
		this.value.getObservable().addObserver(new Observer() {
			@Override
			public void update(Observable o, Object arg) {
				target.set(value.get());
			}
		});
	}
}
