package com.aajtech.model.bind;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.aajtech.model.core.api.Value;
import com.aajtech.model.core.impl.java.JavaType;

public class BindTest {
	@Test
	public void test() throws Exception {
		Value<Integer> source = JavaType.INTEGER.emptyValue();
		Value<String> target = JavaType.STRING.emptyValue();

		Bind.on(source)
			.map((Integer value) -> value.toString())
			.to(target);

		source.set(123);

		assertEquals("123", target.get());
	}
}
