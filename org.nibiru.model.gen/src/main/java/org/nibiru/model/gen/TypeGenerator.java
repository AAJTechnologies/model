package org.nibiru.model.gen;

import com.google.common.base.CaseFormat;
import com.google.common.base.Joiner;
import com.google.common.collect.Iterables;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterizedTypeName;
import com.squareup.javapoet.TypeSpec;

import org.nibiru.model.core.api.Type;
import org.nibiru.model.core.impl.BaseComplexType;
import org.nibiru.model.core.impl.java.JavaType;

import java.lang.reflect.Method;
import java.util.Map;
import java.util.Set;

import javax.lang.model.element.Modifier;

import sun.reflect.generics.tree.BaseType;


public class TypeGenerator {
    private static final int GET_SET_PREFIX_LENGTH = 3;
    private static final String PROPERTY_SUFFIX = "_PROPERTY";

    public String generateType(Class<?> clazz) {
        String typeName = clazz.getName() + "Type";
        TypeSpec.Builder typeBuilder = TypeSpec.classBuilder(typeName)
                .addModifiers(Modifier.PUBLIC)
                .superclass(ParameterizedTypeName.get(ClassName.get(BaseComplexType.class),
                        ClassName.get(clazz)));

        Map<String, Class<?>> fields = Maps.newHashMap();
        for (Map.Entry<String, Class<?>> entry : getFields(clazz).entrySet()) {
            fields.put(CaseFormat.LOWER_CAMEL
                            .to(CaseFormat.UPPER_UNDERSCORE, entry.getKey()),
                    entry.getValue());
        }

        typeBuilder.addField(ClassName.get("", typeName),
                "INSTANCE",
                Modifier.STATIC, Modifier.FINAL);

        for (String field : fields.keySet()) {
            typeBuilder.addField(String.class,
                    field + PROPERTY_SUFFIX,
                    Modifier.STATIC, Modifier.FINAL);
        }

        MethodSpec.Builder constructorBuilder = MethodSpec.constructorBuilder()
                .addModifiers(Modifier.PRIVATE)
                .addStatement("super($L.class,\n" +
                        "                ImmutableMap.of($L))", clazz.getName(), buildFieldMapSpec(fields));

        typeBuilder.addMethod(constructorBuilder.build());

        JavaFile javaFile = JavaFile.builder(clazz.getPackage().getName(), typeBuilder.build())
                .build();

        return javaFile.toString();
    }

    private String buildFieldMapSpec(Map<String, Class<?>> fields) {
        return Joiner.on(",").join(Iterables.transform(fields.entrySet(), (entry) -> {
            return entry.getKey() + PROPERTY_SUFFIX + ", " + getType(entry.getValue());
        }));
    }

    private String getType(Class<?> value) {
        if (String.class.equals(value)) {
            return JavaType.class.getName() + (".STRING");
        } else {
            return value.getName() + "Type.INSTANCE";
        }
    }

    private Map<String, Class<?>> getFields(Class<?> clazz) {
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
