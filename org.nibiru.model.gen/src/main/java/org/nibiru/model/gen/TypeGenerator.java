package org.nibiru.model.gen;

import com.google.common.base.CaseFormat;
import com.google.common.base.Joiner;
import com.google.common.collect.Iterables;
import com.google.common.collect.Maps;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterizedTypeName;
import com.squareup.javapoet.TypeSpec;

import org.nibiru.model.core.impl.BaseComplexType;
import org.nibiru.model.core.impl.java.JavaType;

import java.util.Map;

import javax.lang.model.element.Modifier;


public class TypeGenerator extends BaseGenerator {
    static final String INSTANCE_VAR = "INSTANCE";
    static final String PROPERTY_SUFFIX = "_PROPERTY";

    @Override
    TypeSpec generateCode(Class<?> clazz) {
        String typeName = clazz.getSimpleName() + "Type";
        TypeSpec.Builder typeBuilder = TypeSpec.classBuilder(typeName)
                .addModifiers(Modifier.PUBLIC)
                .superclass(ParameterizedTypeName.get(ClassName.get(BaseComplexType.class),
                        ClassName.get(clazz)));

        Map<String, Class<?>> fields = Maps.newHashMap();
        for (Map.Entry<String, Class<?>> entry : getFields(clazz).entrySet()) {
            String field = entry.getKey();
            Class<?> type = entry.getValue();

            fields.put(CaseFormat.LOWER_CAMEL.to(CaseFormat.UPPER_UNDERSCORE, field),
                    type);
        }

        typeBuilder.addField(ClassName.get("", typeName),
                INSTANCE_VAR,
                Modifier.STATIC, Modifier.FINAL);

        for (String field : fields.keySet()) {
            typeBuilder.addField(String.class,
                    field + PROPERTY_SUFFIX,
                    Modifier.STATIC, Modifier.FINAL);
        }

        typeBuilder.addMethod(MethodSpec.constructorBuilder()
                .addModifiers(Modifier.PRIVATE)
                .addStatement("super($L.class,\n" +
                        "                ImmutableMap.of($L))", clazz.getSimpleName(), buildFieldMapSpec(fields))
                .build());

        return  typeBuilder.build();
    }

    private String buildFieldMapSpec(Map<String, Class<?>> fields) {
        return Joiner.on(",").join(Iterables.transform(fields.entrySet(), entry -> entry.getKey() + PROPERTY_SUFFIX + ", " + getType(entry.getValue())));
    }

    private String getType(Class<?> value) {
        if (String.class.equals(value)) {
            return JavaType.class.getName() + (".STRING");
        } else {
            return value.getName() + "Type.INSTANCE";
        }
    }

}
