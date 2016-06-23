package com.aajtech.model.core.impl.bind;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.nibiru.model.core.api.Value;
import org.nibiru.model.core.impl.bind.Bind;
import org.nibiru.model.core.impl.java.JavaType;
import org.nibiru.model.core.impl.java.JavaValue;

public class BindTest {
	@Test
	public void test() throws Exception {
		Value<Integer> source = JavaValue.of(JavaType.INTEGER);
		Value<String> target = JavaValue.of(JavaType.STRING);

		Bind.on(source)
			.map((Integer value) -> value.toString())
			.to(target);

		source.set(123);

		assertEquals("123", target.get());
	}
}
