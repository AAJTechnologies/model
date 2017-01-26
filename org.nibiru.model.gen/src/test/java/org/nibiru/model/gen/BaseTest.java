package org.nibiru.model.gen;

import com.google.testing.compile.CompilationRule;
import com.squareup.javapoet.JavaFile;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import javax.lang.model.util.Elements;

import static org.junit.Assert.assertEquals;

public abstract class BaseTest {
    private Generator generator;
    @Rule
    public CompilationRule rule = new CompilationRule();
    private Elements elements;

    @Before
    public void setUp() {
        generator = buildGenerator();
        elements = rule.getElements();
    }

    abstract Generator buildGenerator();

    void testCode(Class<?> clazz, String expectedCode) {
        String code = JavaFile.builder(clazz.getPackage().getName(),
                generator.generate(elements.getTypeElement(clazz.getName())))
                .build().toString();

        assertEquals(expectedCode, code);
    }
}
