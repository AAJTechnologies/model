package com.aajtech.model.core.impl.dynamic;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.aajtech.model.core.api.Property;
import com.aajtech.model.core.api.Value;
import com.aajtech.model.core.impl.java.Address;
import com.aajtech.model.core.impl.java.JavaType;
import com.aajtech.model.core.impl.java.JavaValue;

public class DynamicValueTests {
	@Test
	public void test() throws Exception {
		DynamicType personType = new DynamicType("Person", "com.aajtech.model.core.impl.dynamic");
		Property<Object, String> nameProperty = new DynamicProperty<>("name", new JavaType<>(String.class), personType);

		Value<Object> personValue = personType.create();
		personValue.set(nameProperty, new JavaValue<>("pepe"));

		assertEquals("pepe", personValue.get(nameProperty).cast());

		DynamicType addressType = new DynamicType("Address", "com.aajtech.model.core.impl.dynamic");
		Property<Object, Object> addressProperty = new DynamicProperty<>("address", addressType, personType);
		Property<Object, String> streetProperty = new DynamicProperty<>("street", new JavaType<>(String.class),
				addressType);

		personValue.set(addressProperty, addressType.create());
		personValue.get(addressProperty).set(streetProperty, new JavaValue<>("avenida siemprevivas"));
		assertEquals("avenida siemprevivas", personValue.get(addressProperty).get(streetProperty).cast());

		Value<Address> otherAdderss = Address.TYPE.create();
		personValue.set(addressProperty, otherAdderss);
		personValue.get(addressProperty).set(streetProperty, new JavaValue<>("fake street"));
		assertEquals("fake street", personValue.get(addressProperty).get(streetProperty).cast());
		assertTrue(personValue.get(addressProperty).cast() instanceof Address);
	}
}
