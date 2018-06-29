package org.nibiru.model.core.api;

public interface Type<T> {
	Class<T> cast();
	
	String getName();

	String getSimpleName();

	String getNamespace();
}