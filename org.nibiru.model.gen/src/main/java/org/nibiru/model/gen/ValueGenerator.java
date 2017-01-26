package org.nibiru.model.gen;

import com.google.common.base.CaseFormat;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterSpec;
import com.squareup.javapoet.ParameterizedTypeName;
import com.squareup.javapoet.TypeSpec;
import com.squareup.javapoet.TypeVariableName;

import org.nibiru.model.core.api.Property;
import org.nibiru.model.core.api.Type;
import org.nibiru.model.core.api.Value;
import org.nibiru.model.core.impl.BaseComplexValue;

import java.util.Map;

import javax.annotation.Nullable;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;

public class ValueGenerator extends BaseGenerator {
    private static final TypeVariableName X_TYPE = TypeVariableName.get("X");
    private static final String PROPERTY_VAR = "property";

    @Override
    TypeSpec generateCode(TypeElement clazz) {
        String className = clazz.getSimpleName().toString();
        String valueName = className + "Value";
        String implName = className + "Impl";
        String typeName = className + "Type";
        String objectVar = className.substring(0, 1).toLowerCase() + className.substring(1);

        TypeSpec.Builder typeBuilder = TypeSpec.classBuilder(valueName)
                .addModifiers(Modifier.PUBLIC)
                .superclass(ParameterizedTypeName.get(ClassName.get(BaseComplexValue.class),
                        ClassName.get(clazz)));

        typeBuilder.addField(ClassName.get("", implName),
                objectVar,
                Modifier.PRIVATE);

        MethodSpec.Builder getBuilder = MethodSpec.methodBuilder("get")
                .addModifiers(Modifier.PUBLIC)
                .addAnnotation(Override.class)
                .addTypeVariable(X_TYPE)
                .addParameter(ParameterizedTypeName.get(ClassName.get(Property.class), ClassName.get(clazz), X_TYPE), PROPERTY_VAR)
                .returns(ParameterizedTypeName.get(ClassName.get(Value.class), X_TYPE))
                .addStatement("com.google.common.base.Preconditions.checkNotNull(property)");
        getBuilder.addCode("switch ($L.getName()) {\n", PROPERTY_VAR);
        for (Map.Entry<String, TypeElement> entry : getFields(clazz).entrySet()) {
            String field = entry.getKey();
            TypeElement type = entry.getValue();

            getBuilder.addCode("  case $L.$L:\n", typeName, CaseFormat.LOWER_CAMEL.to(CaseFormat.UPPER_UNDERSCORE, field) + TypeGenerator.PROPERTY_SUFFIX);
            getBuilder.addStatement("    return (Value<X>) $L.$L", objectVar, field);
        }
        getBuilder.addCode("  default:\n");
        getBuilder.addStatement("    throw new IllegalArgumentException(\"Invalid property: \" + $L)", PROPERTY_VAR);
        getBuilder.addCode("}\n");
        typeBuilder.addMethod(getBuilder.build());


        typeBuilder.addMethod(MethodSpec.methodBuilder("setValue")
                .addModifiers(Modifier.PROTECTED)
                .addAnnotation(Override.class)
                .addParameter(ParameterSpec.builder(ClassName.get(clazz), objectVar)
                        .addAnnotation(Nullable.class)
                        .build())
                .addStatement("this.$L = ($L) $L", objectVar, implName, objectVar)
                .build());

        typeBuilder.addMethod(MethodSpec.methodBuilder("get")
                .addModifiers(Modifier.PUBLIC)
                .addAnnotation(Override.class)
                .addAnnotation(Nullable.class)
                .returns(ClassName.get(clazz))
                .addStatement("return $L", objectVar)
                .build());

        typeBuilder.addMethod(MethodSpec.methodBuilder("getType")
                .addModifiers(Modifier.PUBLIC)
                .addAnnotation(Override.class)
                .returns(ParameterizedTypeName.get(ClassName.get(Type.class), ClassName.get(clazz)))
                .addStatement("return $L.$L", typeName, TypeGenerator.INSTANCE_VAR)
                .build());

        return typeBuilder.build();
    }
}
