package org.nibiru.model.core.impl.bind;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Iterables;
import org.junit.Test;
import org.nibiru.model.core.api.Value;
import org.nibiru.model.core.impl.java.JavaType;
import org.nibiru.model.core.impl.java.JavaValue;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

public class IterableBindTests {
    @Test
    public void test() {
        Value<Iterable<Integer>> source = JavaValue.of(JavaType.ofIterable(Integer.class));
        Value<Iterable<String>> target = JavaValue.of(new ArrayList<>());

        IterableBind.on(source)
                .map(Object::toString)
                .to(target);

        source.set(ImmutableList.of(1, 2, 3));

        assertEquals(3, Iterables.size(target.get()));
        assertEquals("1", Iterables.get(target.get(), 0));
        assertEquals("2", Iterables.get(target.get(), 1));
        assertEquals("3", Iterables.get(target.get(), 2));
    }
}
