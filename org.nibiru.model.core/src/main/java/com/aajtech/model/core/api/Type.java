package com.aajtech.model.core.api;

public interface Type<T> {
	Class<T> cast();
	
	String getName();

	String getNamespace();
}