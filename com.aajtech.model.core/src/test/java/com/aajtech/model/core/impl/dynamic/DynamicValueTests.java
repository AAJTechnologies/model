package com.aajtech.model.core.impl.dynamic;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.aajtech.model.core.api.ComplexValue;
import com.aajtech.model.core.api.Property;
import com.aajtech.model.core.api.Value;
import com.aajtech.model.core.impl.java.Address;
import com.aajtech.model.core.impl.java.JavaType;
import com.aajtech.model.core.impl.java.JavaValue;

public class DynamicValueTests {
	@Test
	public void test() throws Exception {
		DynamicType personType = DynamicType.of("Person", "com.aajtech.model.core.impl.dynamic");
		Property<Object, String> nameProperty = new DynamicProperty<>("name", JavaType.of(String.class), personType);

		ComplexValue<Object> personValue = DynamicValue.of(personType);
		personValue.set(nameProperty, JavaValue.of("pepe"));

		assertEquals("pepe", personValue.get(nameProperty).get());

		DynamicType addressType = DynamicType.of("Address", "com.aajtech.model.core.impl.dynamic");
		Property<Object, Object> addressProperty = new DynamicProperty<>("address", addressType, personType);
		Property<Object, String> streetProperty = new DynamicProperty<>("street", JavaType.of(String.class),
				addressType);

		personValue.set(addressProperty, JavaValue.of(new Address()));
		personValue.getComplex(addressProperty).set(streetProperty, JavaValue.of("avenida siemprevivas"));
		assertEquals("avenida siemprevivas", personValue.getComplex(addressProperty).get(streetProperty).get());

		Value<Address> otherAdderss = JavaValue.of(new Address());
		personValue.set(addressProperty, otherAdderss);
		personValue.getComplex(addressProperty).set(streetProperty, JavaValue.of("fake street"));
		assertEquals("fake street", personValue.getComplex(addressProperty).get(streetProperty).get());
		assertTrue(personValue.get(addressProperty).get() instanceof Address);
	}
}
