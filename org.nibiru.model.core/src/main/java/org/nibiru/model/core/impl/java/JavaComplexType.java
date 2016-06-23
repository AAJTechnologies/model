package org.nibiru.model.core.impl.java;

import static com.google.common.base.Preconditions.checkNotNull;

import java.lang.reflect.Field;
import java.util.Arrays;

import org.nibiru.model.core.api.ComplexType;
import org.nibiru.model.core.api.Property;

import com.google.common.annotations.GwtIncompatible;
import com.google.common.collect.Iterables;

@GwtIncompatible("Reflection")
public class JavaComplexType<T> extends JavaType<T>implements ComplexType<T> {
	public static <X> JavaComplexType<X> of(Class<X> javaClass) {
		checkNotNull(javaClass);
		return new JavaComplexType<>(javaClass);
	}

	private JavaComplexType(Class<T> javaClass) {
		super(javaClass);
	}

	@Override
	public Iterable<Property<T, ?>> getProperties() {
		return Iterables.transform(Arrays.asList(getJavaClass().getDeclaredFields()),
				(Field field) -> JavaProperty.of(field));
	}

	@Override
	public <X> Property<T, X> getProperty(String name) {
		checkNotNull(name);
		return JavaProperty.of(field(name));
	}

	Field field(String name) {
		try {
			Field field = getJavaClass().getDeclaredField(name);
			field.setAccessible(true);
			return field;
		} catch (NoSuchFieldException | SecurityException e) {
			throw new IllegalArgumentException("Invalid field: " + name, e);
		}
	}

}
