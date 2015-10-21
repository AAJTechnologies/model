package com.aajtech.model.core.impl;

import static com.google.common.base.Preconditions.checkNotNull;

import java.util.Collection;
import java.util.Objects;

import javax.annotation.Nullable;

import com.aajtech.model.core.api.Registration;
import com.aajtech.model.core.api.Value;
import com.google.common.collect.Sets;

public abstract class BaseValue<T> implements Value<T> {
	protected final Collection<Runnable> observers = Sets.newHashSet();

	@Override
	public void set(T value) {
		if (!Objects.equals(value, get())) {
			setValue(value);
			notifyObservers();
		}
	}

	protected abstract void setValue(@Nullable T value);

	@Override
	public Registration addObserver(Runnable observer) {
		checkNotNull(observer);
		observers.add(observer);
		return new Registration() {
			@Override
			public void remove() {
				observers.remove(observer);
			}
		};
	}

	protected void notifyObservers() {
		for (Runnable observer : observers) {
			observer.run();
		}
	}

	@Override
	public int hashCode() {
		return Objects.hash(get());
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		BaseValue<?> that = (BaseValue<?>) o;
		return Objects.equals(get(), that.get());
	}
}
