package org.nibiru.model.core.impl.java;

import org.junit.Test;
import org.nibiru.model.core.api.Value;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class JavaValueTests {
    @Test
    public void test() {
        Value<String> value = JavaValue.of("Hi!");
        assertEquals("Hi!", value.get());

        value.set("Bye!");
        assertEquals("Bye!", value.get());

        value = JavaValue.of(JavaType.STRING);
        assertNull(value.get());

        value.set("Not null");
        assertEquals("Not null", value.get());
    }

    @Test
    public void notifyTest() {
        Value<String> value = JavaValue.of("Hi!");
        value.addObserver(() ->
                assertEquals("Bye!", value.get())
        );
        value.set("Bye!");
    }
}
