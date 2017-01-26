package org.nibiru.model.gen;

import com.squareup.javapoet.TypeSpec;

import javax.lang.model.element.TypeElement;

public interface Generator {
    TypeSpec generate(TypeElement clazz);
}
