package com.aajtech.model.core.impl.dynamic;

import static com.google.common.base.Preconditions.checkNotNull;


import com.aajtech.model.core.api.Property;
import com.aajtech.model.core.api.Type;
import com.google.common.base.Objects;

public class DynamicProperty<P> implements Property<Object, P> {
	public static <X> DynamicProperty<X> of(String name, Type<X> type, DynamicType parent) {
		return new DynamicProperty<>(name, type, parent);
	}

	private final String name;
	private final Type<Object> parent;
	private final Type<P> type;

	protected DynamicProperty(String name, Type<P> type, DynamicType parent) {
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
		return Objects.hashCode(name);
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
		return Objects.equal(name, that.name) && Objects.equal(type, that.type);
	}

	@Override
	public String toString() {
		return "DynamicProperty [name=" + name + "]";
	}
}
