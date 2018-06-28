package org.nibiru.model.core.impl;

import com.google.common.base.Objects;
import com.google.common.collect.Sets;

import org.nibiru.model.core.api.Registration;
import org.nibiru.model.core.api.Type;
import org.nibiru.model.core.api.Value;
import org.nibiru.model.core.impl.java.JavaType;

import java.util.Collection;

import javax.annotation.Nullable;

import static com.google.common.base.Preconditions.checkNotNull;

public abstract class BaseValue<T> implements Value<T> {
    private final Collection<Runnable> observers = Sets.newHashSet();

    public static <X> BaseValue<X> of(X initialValue) {
        return new BaseValue<X>() {
            private X value = initialValue;

            @Override
            public X get() {
                return value;
            }

            @Override
            public Type<X> getType() {
                return JavaType.of((Class<X>) initialValue.getClass());
            }

            @Override
            protected void setValue(X value) {
                this.value = value;
            }
        };
    }

    public static <X> BaseValue<X> of(Type<X> type) {
        return new BaseValue<X>() {
            private X value;

            @Override
            public X get() {
                return value;
            }

            @Override
            public Type<X> getType() {
                return type;
            }

            @Override
            protected void setValue(X value) {
                this.value = value;
            }
        };
    }

    @Override
    public void set(T value) {
        if (!Objects.equal(value, get())) {
            setValue(value);
            notifyObservers();
        }
    }

    protected abstract void setValue(@Nullable T value);

    @Override
    public Registration addObserver(final Runnable observer) {
        checkNotNull(observer);
        observers.add(observer);
        return () -> observers.remove(observer);
    }

    @Override
    public void notifyObservers() {
        for (Runnable observer : observers) {
            observer.run();
        }
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(get());
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
        return Objects.equal(get(), that.get());
    }
}
