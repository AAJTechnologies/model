package org.nibiru.model.gen;

import com.google.common.base.CaseFormat;
import com.google.common.base.Joiner;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Iterables;
import com.google.common.collect.Maps;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterizedTypeName;
import com.squareup.javapoet.TypeSpec;

import org.nibiru.model.core.impl.BaseComplexType;
import org.nibiru.model.core.impl.java.JavaType;

import java.util.Map;

import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;


public class TypeGenerator extends BaseGenerator {
    static final String INSTANCE_VAR = "INSTANCE";
    static final String PROPERTY_SUFFIX = "_PROPERTY";

    @Override
    TypeSpec generateCode(TypeElement clazz) {
        String typeName = clazz.getSimpleName() + "Type";
        TypeSpec.Builder typeBuilder = TypeSpec.classBuilder(typeName)
                .addModifiers(Modifier.PUBLIC)
                .superclass(ParameterizedTypeName.get(ClassName.get(BaseComplexType.class),
                        ClassName.get(clazz)));

        Map<String, TypeElement> fields = getFields(clazz);

        typeBuilder.addField(FieldSpec.builder(ClassName.get("", typeName),
                INSTANCE_VAR,
                Modifier.STATIC, Modifier.FINAL)
                .initializer("new $L()", typeName)
                .build());

        for (String field : fields .keySet()) {
            typeBuilder.addField(FieldSpec.builder(String.class, CaseFormat.LOWER_CAMEL.to(CaseFormat.UPPER_UNDERSCORE, field) + PROPERTY_SUFFIX, Modifier.STATIC, Modifier.FINAL)
                    .initializer("\"$L\"", field)
                    .build());
        }

        typeBuilder.addMethod(MethodSpec.constructorBuilder()
                .addModifiers(Modifier.PRIVATE)
                .addStatement("super($L.class,\n" +
                                "                $L.of($L))",
                        clazz.getSimpleName(),
                        ImmutableMap.class.getName(),
                        buildFieldMapSpec(fields ))
                .build());

        return typeBuilder.build();
    }

    private String buildFieldMapSpec(Map<String, TypeElement> fields) {
        return Joiner.on(",")
                .join(Iterables.transform(fields.entrySet(), entry -> CaseFormat.LOWER_CAMEL.to(CaseFormat.UPPER_UNDERSCORE, entry.getKey()) + PROPERTY_SUFFIX + ", " + getType(entry.getValue())));
    }

    private String getType(TypeElement value) {
        if (String.class.getName().equals(value.getQualifiedName().toString())) {
            return JavaType.class.getName() + (".STRING");
        } else {
            return value.getQualifiedName() + "Type.INSTANCE";
        }
    }
}
