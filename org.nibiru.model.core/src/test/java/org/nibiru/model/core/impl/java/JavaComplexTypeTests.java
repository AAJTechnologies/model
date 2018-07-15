package org.nibiru.model.core.impl.java;

import org.junit.Test;
import org.nibiru.model.core.api.ComplexType;
import org.nibiru.model.core.api.Property;

import static org.junit.Assert.assertEquals;

public class JavaComplexTypeTests {
    @Test
    public void testOf() throws Exception {
        ComplexType<Person> type = JavaComplexType.of(Person.class);

        assertEquals("org.nibiru.model.core.impl.java.Person", type.getName());
        assertEquals("Person", type.getSimpleName());
        assertEquals("org.nibiru.model.core.impl.java", type.getNamespace());
        assertEquals(Person.class, type.cast());

        Property<Person, String> nameProperty = type.getProperty("name");
        assertEquals("java.lang.String", nameProperty.getType().getName());
        assertEquals("String", nameProperty.getType().getSimpleName());
        assertEquals("java.lang", nameProperty.getType().getNamespace());
    }
}
