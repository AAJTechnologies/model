package com.aajtech.model.core.impl.dynamic;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.nibiru.model.core.api.ComplexValue;
import org.nibiru.model.core.api.Property;
import org.nibiru.model.core.api.Value;
import org.nibiru.model.core.impl.dynamic.DynamicProperty;
import org.nibiru.model.core.impl.dynamic.DynamicType;
import org.nibiru.model.core.impl.dynamic.DynamicValue;
import org.nibiru.model.core.impl.java.JavaComplexValue;
import org.nibiru.model.core.impl.java.JavaType;
import org.nibiru.model.core.impl.java.JavaValue;

import com.aajtech.model.core.impl.java.Address;

public class DynamicValueTests {
	@Test
	public void test() throws Exception {
		DynamicType personType = DynamicType.of("Person", "org.nibiru.model.core.impl.dynamic");
		Property<Object, String> nameProperty = DynamicProperty.of("name", JavaType.of(String.class), personType);

		ComplexValue<Object> personValue = DynamicValue.of(personType);
		personValue.set(nameProperty, JavaValue.of("pepe"));

		assertEquals("pepe", personValue.get(nameProperty).get());

		DynamicType addressType = DynamicType.of("Address", "org.nibiru.model.core.impl.dynamic");
		Property<Object, Object> addressProperty = DynamicProperty.of("address", addressType, personType);
		Property<Object, String> streetProperty = DynamicProperty.of("street", JavaType.of(String.class),
				addressType);

		personValue.set(addressProperty, JavaComplexValue.of(new Address()));
		personValue.getComplex(addressProperty).set(streetProperty, JavaValue.of("avenida siemprevivas"));
		assertEquals("avenida siemprevivas", personValue.getComplex(addressProperty).get(streetProperty).get());

		Value<Address> otherAdderss = JavaComplexValue.of(new Address());
		personValue.set(addressProperty, otherAdderss);
		personValue.getComplex(addressProperty).set(streetProperty, JavaValue.of("fake street"));
		assertEquals("fake street", personValue.getComplex(addressProperty).get(streetProperty).get());
		assertTrue(personValue.get(addressProperty).get() instanceof Address);
	}
}
