package org.nibiru.model.core.impl.dynamic;

import static com.google.common.base.Preconditions.checkNotNull;

import java.util.Map;

import javax.annotation.Nullable;

import org.nibiru.model.core.api.ComplexValue;
import org.nibiru.model.core.api.Property;
import org.nibiru.model.core.api.Type;
import org.nibiru.model.core.api.Value;
import org.nibiru.model.core.impl.BaseValue;

import com.google.common.base.Objects;
import com.google.common.collect.Maps;

public class DynamicValue extends BaseValue<Object>implements ComplexValue<Object> {
	public static DynamicValue of(DynamicType type) {
		return new DynamicValue(type);
	}

	private Object value;
	private final DynamicType type;
	private final Map<String, Value<?>> values;

	protected DynamicValue(DynamicType type) {
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
	protected void setValue(@Nullable Object value) {
		this.value = value;
	}

	@Override
	public <X> ComplexValue<Object> set(Property<Object, X> property, Value<? extends X> value) {
		String name = checkNotNull(property).getName();
		values.put(name, value);
		if (!Objects.equal(values.get(name), value)) {
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
