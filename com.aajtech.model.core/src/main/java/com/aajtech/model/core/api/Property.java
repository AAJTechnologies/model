package com.aajtech.model.core.api;

import com.google.common.base.Function;

public interface Property<T, P> {
	static final Function<Property<?, ?>, String> TO_NAME = (Property<?, ?> property) -> property.getName();

	static final Function<Property<?, ?>, Type<?>> TO_TYPE = (Property<?, ?> property) -> property.getType();

	String getName();

	Type<P> getType();

	Type<T> getParent();

}