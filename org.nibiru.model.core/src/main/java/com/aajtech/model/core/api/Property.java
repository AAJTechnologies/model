package com.aajtech.model.core.api;

public interface Property<T, P> {
	String getName();

	Type<P> getType();

	Type<T> getParent();
}