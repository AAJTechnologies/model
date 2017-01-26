package org.nibiru.model.core.impl;

import com.google.common.base.Objects;

import org.nibiru.model.core.api.Property;
import org.nibiru.model.core.api.Type;

import static com.google.common.base.Preconditions.checkNotNull;

public class BaseProperty<T, P> implements Property<T, P> {
    private final String name;
    private final Type<P> type;
    private final Type<T> parent;

    public BaseProperty(String name, Type<P> type, Type<T> parent) {
        this.name = checkNotNull(name);
        this.type = checkNotNull(type);
        this.parent = checkNotNull(parent);
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public Type<P> getType() {
        return type;
    }

    @Override
    public Type<T> getParent() {
        return parent;
    }


    @Override
    public int hashCode() {
        return Objects.hashCode(name, type, parent);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        BaseProperty<?, ?> that = (BaseProperty<?, ?>) o;
        return Objects.equal(name, that.name)
                && Objects.equal(type, that.type)
                && Objects.equal(parent, that.parent);
    }
}
