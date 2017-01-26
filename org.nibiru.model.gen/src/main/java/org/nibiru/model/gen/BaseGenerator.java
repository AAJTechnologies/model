package org.nibiru.model.gen;


import com.google.common.collect.Maps;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.TypeSpec;

import java.lang.reflect.Method;
import java.util.Map;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

abstract class BaseGenerator implements Generator {
    private static final int GET_SET_PREFIX_LENGTH = 3;

    @Override
    public final String generate(Class<?> clazz) {
        checkNotNull(clazz);
        checkArgument(clazz.isInterface(), "Class must be an interface");

        return JavaFile.builder(clazz.getPackage().getName(), generateCode(clazz))
                .build().toString();
    }

    abstract TypeSpec generateCode(Class<?> clazz);

    Map<String, Class<?>> getFields(Class<?> clazz) {
        Map<String, Class<?>> fields = Maps.newHashMap();
        for (Method method : clazz.getMethods()) {
            String name = method.getName();
            boolean isGetter = name.startsWith("get");
            boolean isSetter = name.startsWith("set");
            if ((isGetter || isSetter)
                    && name.length() > GET_SET_PREFIX_LENGTH) {

                Class<?> fieldType;
                if (isGetter) {
                    if (method.getParameterCount() != 0) {
                        throw new IllegalArgumentException("Invalid method: " + method + " - getters must have 0 parameter");
                    }
                    fieldType = method.getReturnType();
                } else {
                    if (method.getParameterCount() != 1) {
                        throw new IllegalArgumentException("Invalid method: " + method + " - setters must have 1 parameter");
                    }
                    fieldType = method.getParameterTypes()[0];
                }
                Class<?> existingFieldType = fields.get(name);
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
