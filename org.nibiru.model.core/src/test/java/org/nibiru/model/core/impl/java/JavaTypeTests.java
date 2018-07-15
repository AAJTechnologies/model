package org.nibiru.model.core.impl.java;

import org.junit.Test;
import org.nibiru.model.core.api.Type;

import java.lang.ref.Reference;
import java.util.Date;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.assertEquals;

public class JavaTypeTests {
    @Test
    public void testOf() throws Exception {
        Type<String> type = JavaType.of(String.class);

        assertEquals("java.lang.String", type.getName());
        assertEquals("String", type.getSimpleName());
        assertEquals("java.lang", type.getNamespace());
        assertEquals(String.class, type.cast());
    }

    @Test
    public void testConstants() throws Exception {
        assertEquals("java.lang.String", JavaType.STRING.getName());
        assertEquals("String", JavaType.STRING.getSimpleName());
        assertEquals("java.lang", JavaType.STRING.getNamespace());
        assertEquals(String.class, JavaType.STRING.cast());

        assertEquals("java.lang.Boolean", JavaType.BOOLEAN.getName());
        assertEquals("Boolean", JavaType.BOOLEAN.getSimpleName());
        assertEquals("java.lang", JavaType.BOOLEAN.getNamespace());
        assertEquals(Boolean.class, JavaType.BOOLEAN.cast());

        assertEquals("java.lang.Integer", JavaType.INTEGER.getName());
        assertEquals("Integer", JavaType.INTEGER.getSimpleName());
        assertEquals("java.lang", JavaType.INTEGER.getNamespace());
        assertEquals(Integer.class, JavaType.INTEGER.cast());

        assertEquals("java.util.Date", JavaType.DATE.getName());
        assertEquals("Date", JavaType.DATE.getSimpleName());
        assertEquals("java.util", JavaType.DATE.getNamespace());
        assertEquals(Date.class, JavaType.DATE.cast());
    }

    @Test
    public void testIterable() throws Exception {
        Type<Iterable<String>> type = JavaType.ofIterable(String.class);

        assertEquals("java.lang.Iterable", type.getName());
        assertEquals("Iterable", type.getSimpleName());
        assertEquals("java.lang", type.getNamespace());
        assertEquals(Iterable.class, type.cast());
    }

    @Test
    public void testList() throws Exception {
        Type<List<String>> type = JavaType.ofList(String.class);

        assertEquals("java.util.List", type.getName());
        assertEquals("List", type.getSimpleName());
        assertEquals("java.util", type.getNamespace());
        assertEquals(List.class, type.cast());
    }


    @Test
    public void testUnchecked() throws Exception {
        Type<List<Iterable<Set<Reference<Date>>>>> type = JavaType.ofUnchecked(List.class);

        assertEquals("java.util.List", type.getName());
        assertEquals("List", type.getSimpleName());
        assertEquals("java.util", type.getNamespace());
        assertEquals(List.class, type.cast());
    }
}
