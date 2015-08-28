package com.aajtech.model.core.impl.dynamic;

import static com.google.common.base.Preconditions.checkNotNull;

import java.util.Map;
import java.util.Objects;

import javax.annotation.Nullable;

import com.aajtech.model.core.api.Property;
import com.aajtech.model.core.api.Type;
import com.aajtech.model.core.api.Value;
import com.aajtech.model.core.impl.BaseValue;
import com.google.common.collect.Maps;

public class DynamicValue extends BaseValue<Object> {
	private Object value;
	private final DynamicType type;
	private final Map<String, Value<?>> values;

	public DynamicValue(DynamicType type) {
		this.type = checkNotNull(type);
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
	public Value<Object> set(@Nullable Object value) {
		Object oldValue = this.value;
		this.value = value;
		if (!Objects.equals(oldValue, value)) {
			observable.setChanged();
			observable.notifyObservers();
		}
		return this;
	}

	@Override
	public <X> Value<Object> set(Property<Object, X> property, Value<? extends X> value) {
		String name = checkNotNull(property).getName();
		values.put(name, value);
		if (!Objects.equals(values.get(name), value)) {
			observable.setChanged();
			observable.notifyObservers();
		}
		return this;
	}

	@SuppressWarnings("unchecked")
	@Override
	public <X> Value<X> get(Property<Object, X> property) {
		checkNotNull(property);
		return (Value<X>) values.get(property.getName());
	}
}
