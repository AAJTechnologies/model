package com.aajtech.model.core.impl.dynamic;

import static com.google.common.base.Preconditions.checkNotNull;

import java.util.Map;
import java.util.Objects;

import com.aajtech.model.core.api.ComplexType;
import com.aajtech.model.core.api.Property;
import com.google.common.collect.Maps;

public class DynamicType implements ComplexType<Object> {
	public static DynamicType of(String name, String namespace) {
		checkNotNull(name);
		checkNotNull(namespace);
		return new DynamicType(name, namespace);
	}

	private final String name;
	private final String namespace;
	private final Map<String, Property<Object, ?>> properties;

	private DynamicType(String name, String namespace) {
		this.name = name;
		this.namespace = namespace;
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
		return Objects.hash(name, namespace, properties);
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
		return Objects.equals(name, that.name) && Objects.equals(namespace, that.namespace)
				&& Objects.equals(properties, that.properties);
	}

	@Override
	public String toString() {
		return "DynamicType [name=" + name + ", namespace=" + namespace + ", properties=" + properties + "]";
	}
}
