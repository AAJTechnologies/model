package org.nibiru.model.gen;


import com.google.common.collect.Maps;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.TypeSpec;

import java.util.Map;

import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.util.ElementFilter;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

abstract class BaseGenerator implements Generator {
    private static final int GET_SET_PREFIX_LENGTH = 3;

    @Override
    public final TypeSpec generate(TypeElement clazz) {
        checkNotNull(clazz);
        checkArgument(clazz.getKind().isInterface(), "Class must be an interface");
        return generateCode(clazz);
    }

    abstract TypeSpec generateCode(TypeElement clazz);

    Map<String, TypeElement> getFields(TypeElement clazz) {
        Map<String, TypeElement> fields = Maps.newHashMap();
        for (ExecutableElement method : ElementFilter.methodsIn(clazz.getEnclosedElements())) {
            String name = method.getSimpleName().toString();
            boolean isGetter = name.startsWith("get");
            boolean isSetter = name.startsWith("set");
            if ((isGetter || isSetter)
                    && name.length() > GET_SET_PREFIX_LENGTH) {

                TypeElement fieldType;
                if (isGetter) {
                    if (!method.getParameters().isEmpty()) {
                        throw new IllegalArgumentException("Invalid method: " + method + " - getters must have 0 parameter");
                    }
                    fieldType = (TypeElement) ((DeclaredType)method.getReturnType()).asElement();
                } else {
                    if (method.getParameters().size() != 1) {
                        throw new IllegalArgumentException("Invalid method: " + method + " - setters must have 1 parameter");
                    }
                    fieldType = (TypeElement) ((DeclaredType)method.getParameters().get(0).asType()).asElement();
                }
                TypeElement existingFieldType = fields.get(name);
                if (existingFieldType != null) {
                    if (!existingFieldType.equals(fieldType)) {
                        throw new IllegalArgumentException("Invalid method: " + method + " - setters must have 1 parameter");
                    }
                } else {
                    fields.put(name.substring(GET_SET_PREFIX_LENGTH, GET_SET_PREFIX_LENGTH + 1).toLowerCase()
                            + name.substring(GET_SET_PREFIX_LENGTH + 1), fieldType);
                }
            } else {
                throw new IllegalArgumentException("Invalid method: " + method);
            }
        }
        return fields;
    }
}
