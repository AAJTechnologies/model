package org.nibiru.model.core.impl.dynamic;

import com.google.common.base.Objects;
import com.google.common.collect.Maps;
import org.nibiru.model.core.api.ComplexType;
import org.nibiru.model.core.api.Property;

import java.util.Map;

import static com.google.common.base.Preconditions.checkNotNull;

public class DynamicType implements ComplexType<Object> {
    public static DynamicType of(String name, String namespace) {
        return new DynamicType(name, namespace);
    }

    private final String name;
    private final String namespace;
    private final Map<String, Property<Object, ?>> properties;

    protected DynamicType(String name, String namespace) {
        this.name = checkNotNull(name);
        this.namespace = checkNotNull(namespace);
        this.properties = Maps.newLinkedHashMap();
    }

    @Override
    public Class<Object> cast() {
        return Object.class;
    }

    /**
     * Set after construction to avoid graph cycles.
     */
    void addProperty(DynamicProperty<?> property) {
        properties.put(property.getName(), property);
    }

    @Override
    public String getName() {
        return namespace + "." + name;
    }

    @Override
    public String getSimpleName() {
        return name;
    }

    @Override
    public String getNamespace() {
        return namespace;
    }

    @Override
    public Iterable<Property<Object, ?>> getProperties() {
        return properties.values();
    }

    @SuppressWarnings("unchecked")
    @Override
    public <X> Property<Object, X> getProperty(String name) {
        checkNotNull(name);
        return (Property<Object, X>) properties.get(name);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(name, namespace, properties);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        DynamicType that = (DynamicType) o;
        return Objects.equal(name, that.name)
                && Objects.equal(namespace, that.namespace)
                && Objects.equal(properties, that.properties);
    }

    @Override
    public String toString() {
        return "DynamicType [name=" + name + ", namespace=" + namespace + ", properties=" + properties + "]";
    }
}
