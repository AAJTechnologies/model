package org.nibiru.model.core.impl;

import org.nibiru.model.core.api.ComplexValue;
import org.nibiru.model.core.api.Property;
import org.nibiru.model.core.api.Value;

public abstract class BaseComplexValue<T>
        extends BaseValue<T>
        implements ComplexValue<T> {

    @Override
    public <X> ComplexValue<T> set(Property<T, X> property, Value<? extends X> value) {
        get(property).set(value.get());
        return this;
    }

    @Override
    public <X> ComplexValue<X> getComplex(Property<T, X> property) {
        return (ComplexValue<X>) get(property);
    }
}
