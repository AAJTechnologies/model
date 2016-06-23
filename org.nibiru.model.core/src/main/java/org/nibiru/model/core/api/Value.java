package org.nibiru.model.core.api;

import javax.annotation.Nullable;

public interface Value<T> {
	@Nullable
	T get();

	void set(@Nullable T value);

	Registration addObserver(Runnable observer);

	Type<T> getType();

	void notifyObservers();
}
