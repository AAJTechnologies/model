package org.nibiru.model.core.impl;

import com.google.common.base.Objects;
import com.google.common.collect.Maps;

import org.nibiru.model.core.api.ComplexType;
import org.nibiru.model.core.api.Property;
import org.nibiru.model.core.api.Type;
import org.nibiru.model.core.impl.java.JavaType;

import java.util.Map;

import static com.google.common.base.Preconditions.checkNotNull;

public class BaseComplexType<T> extends JavaType<T> implements ComplexType<T> {
    private final Map<String, Property<T, ?>> properties;

    public BaseComplexType(Class<T> clazz, Map<String, Type<?>> propertyTypes) {
        super(clazz);
        checkNotNull(propertyTypes);
        this.properties = Maps.newHashMap(Maps.transformEntries(propertyTypes,
                (String key, Type<?> value) -> new BaseProperty<>(key, value, this)));
    }

    @Override
    public Iterable<Property<T, ?>> getProperties() {
        return properties.values();
    }

    @Override
    public <X> Property<T, X> getProperty(String name) {
        return (Property<T, X>) properties.get(name);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(properties);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        BaseComplexType<?> that = (BaseComplexType<?>) o;
        return Objects.equal(properties, that.properties);
    }
}
