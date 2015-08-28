package com.aajtech.model.core.impl;

import java.util.Objects;
import java.util.Observable;

import com.aajtech.model.core.api.Value;

public abstract class BaseValue<T> implements Value<T> {
	protected final InnerObservable observable = new InnerObservable();

	@Override
	public Observable observable() {
		return observable;
	}

	@Override
	public int hashCode() {
		return Objects.hash(cast());
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
		return Objects.equals(cast(), that.cast());
	}

	@Override
	public String toString() {
		return "BaseValue [observable=" + observable + "]";
	}

	protected static class InnerObservable extends Observable {
		@Override
		public synchronized void setChanged() {
			super.setChanged();
		}
	}
}
