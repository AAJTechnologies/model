package org.nibiru.model.core.impl.bind;

import static com.google.common.base.Preconditions.checkNotNull;

import org.nibiru.model.core.api.Value;
import org.nibiru.model.core.impl.java.JavaType;
import org.nibiru.model.core.impl.java.JavaValue;

import com.google.common.base.Function;
import com.google.common.collect.Iterables;

public class IterableBind<T> {
	private final Value<Iterable<T>> value;

	private IterableBind(Value<Iterable<T>> value) {
		this.value = value;
	}

	public static <X> IterableBind<X> on(Value<Iterable<X>> value) {
		checkNotNull(value);
		return new IterableBind<>(value);
	}

	public <X> IterableBind<X> map(final Function<T, X> converter) {
		checkNotNull(converter);
		@SuppressWarnings("unchecked")
		final JavaValue<Iterable<X>> targetValue = JavaValue.of(JavaType.ofIterable((Class<X>) Object.class));
		value.addObserver(() -> targetValue.set(Iterables.transform(value.get(), converter)));
		return new IterableBind<>(targetValue);
	}

	public void to(final Value<Iterable<T>> target) {
		value.addObserver(() -> target.set(value.get()));
	}
}
