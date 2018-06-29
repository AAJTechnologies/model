package org.nibiru.model.core.impl.bind;

import org.junit.Test;
import org.nibiru.model.core.api.Value;
import org.nibiru.model.core.impl.java.JavaType;
import org.nibiru.model.core.impl.java.JavaValue;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class BindTests {
    @Test
    public void test() {
        Value<Integer> source = JavaValue.of(JavaType.INTEGER);
        Value<String> target = JavaValue.of(JavaType.STRING);

        Bind.on(source)
                .map(Object::toString)
                .to(target);

        source.set(123);

        assertEquals("123", target.get());
    }

    @Test
    public void notifyTest() {
        Value<Integer> source = JavaValue.of(123);
        Value<String> target = JavaValue.of(JavaType.STRING);

        Bind.on(source)
                .map(Object::toString)
                .to(target);

        assertNull(target.get());

        source.notifyObservers();
        assertEquals("123", target.get());
    }
}
