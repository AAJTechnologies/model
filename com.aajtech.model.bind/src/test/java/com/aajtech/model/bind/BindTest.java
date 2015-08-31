package com.aajtech.model.bind;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.aajtech.model.core.api.Value;
import com.aajtech.model.core.impl.java.JavaType;
import com.aajtech.model.core.impl.java.JavaValue;

public class BindTest {
	@Test
	public void test() throws Exception {
		Value<Integer> source = new JavaValue<>(null, new JavaType<Integer>(Integer.class));
		Value<String> target = new JavaValue<>(null, new JavaType<String>(String.class));

		Bind.on(source)
			.map((Integer value) -> value.toString())
			.to(target);

		source.set(123);

		assertEquals("123", target.get());
	}
}
