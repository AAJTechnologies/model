package org.nibiru.model.core.impl.bind;

import static com.google.common.base.Preconditions.checkNotNull;

import org.nibiru.model.core.api.Value;
import org.nibiru.model.core.impl.java.JavaType;
import org.nibiru.model.core.impl.java.JavaValue;

import com.google.common.base.Converter;
import com.google.common.primitives.Ints;

public final class ConverterUtil {
	private ConverterUtil() {
	}

	public static <Y, X> Value<Y> convert(Value<X> source, Converter<X, Y> converter) {
		checkNotNull(source);
		checkNotNull(converter);
		Value<Y> target = JavaValue.of(JavaType.ofUnchecked(Object.class));
		Bind.on(source).map(converter).to(target);
		Bind.on(target).map(converter.reverse()).to(source);
		source.notifyObservers();
		return target;
	}

	@SuppressWarnings("unchecked")
	public static Value<String> convertToString(Value<?> source) {
		checkNotNull(source);
		if (JavaType.STRING.equals(source.getType())) {
			return (Value<String>) source;
		} else if (JavaType.INTEGER.equals(source.getType())) {
			return convert((Value<Integer>) source, Ints.stringConverter().reverse());
		} else {
			throw new IllegalArgumentException("Unsupported type: " + source.getType());
		}
	}

}
