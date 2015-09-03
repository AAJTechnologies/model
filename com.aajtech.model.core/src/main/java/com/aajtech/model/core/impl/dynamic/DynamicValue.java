package com.aajtech.model.core.impl.dynamic;

import static com.google.common.base.Preconditions.checkNotNull;

import java.util.Map;
import java.util.Objects;

import javax.annotation.Nullable;

import com.aajtech.model.core.api.ComplexValue;
import com.aajtech.model.core.api.Property;
import com.aajtech.model.core.api.Type;
import com.aajtech.model.core.api.Value;
import com.aajtech.model.core.impl.BaseValue;
import com.google.common.collect.Maps;

public class DynamicValue extends BaseValue<Object>implements ComplexValue<Object> {
	public static DynamicValue of(DynamicType type) {
		checkNotNull(type);
		return new DynamicValue(type);
	}

	private Object value;
	private final DynamicType type;
	private final Map<String, Value<?>> values;

	private DynamicValue(DynamicType type) {
		this.type = type;
		this.values = Maps.newHashMap();
	}

	@Override
	public Type<Object> getType() {
		return type;
	}

	@Override
	@Nullable
	public Object get() {
		return value;
	}

	@Override
	public void set(@Nullable Object value) {
		Object oldValue = this.value;
		this.value = value;
		if (!Objects.equals(oldValue, value)) {
			notifyObservers();
		}
	}

	@Override
	public <X> ComplexValue<Object> set(Property<Object, X> property, Value<? extends X> value) {
		String name = checkNotNull(property).getName();
		values.put(name, value);
		if (!Objects.equals(values.get(name), value)) {
			notifyObservers();
		}
		return this;
	}

	@SuppressWarnings("unchecked")
	@Override
	public <X> Value<X> get(Property<Object, X> property) {
		checkNotNull(property);
		return (Value<X>) values.get(property.getName());
	}

	@SuppressWarnings("unchecked")
	@Override
	public <X> ComplexValue<X> getComplex(Property<Object, X> property) {
		checkNotNull(property);
		return (ComplexValue<X>) values.get(property.getName());
	}
}
