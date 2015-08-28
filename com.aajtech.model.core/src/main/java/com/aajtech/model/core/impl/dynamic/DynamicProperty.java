package com.aajtech.model.core.impl.dynamic;

import static com.google.common.base.Preconditions.checkNotNull;

import java.util.Objects;

import com.aajtech.model.core.api.Property;
import com.aajtech.model.core.api.Type;

public class DynamicProperty<P> implements Property<Object, P> {

	private final String name;
	private final Type<Object> parent;
	private final Type<P> type;

	public DynamicProperty(String name, Type<P> type, DynamicType parent) {
		this.name = checkNotNull(name);
		this.type = checkNotNull(type);
		this.parent = checkNotNull(parent);
		parent.addProperty(this);
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
	public Type<Object> getParent() {
		return parent;
	}

	@Override
	public int hashCode() {
		// Not including type to avoid infinite cycles.
		return Objects.hash(name);
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		DynamicProperty<?> that = (DynamicProperty<?>) o;
		return Objects.equals(name, that.name) && Objects.equals(type, that.type);
	}

	@Override
	public String toString() {
		return "DynamicProperty [name=" + name + "]";
	}
}
