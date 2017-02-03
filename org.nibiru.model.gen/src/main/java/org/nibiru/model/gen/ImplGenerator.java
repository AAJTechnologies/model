package org.nibiru.model.gen;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Iterables;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterizedTypeName;
import com.squareup.javapoet.TypeSpec;

import org.nibiru.model.core.api.Value;
import org.nibiru.model.core.impl.java.JavaValue;

import java.util.Map;

import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.util.ElementFilter;

public class ImplGenerator extends BaseGenerator {
    @Override
    TypeSpec generateCode(TypeElement clazz) {
        String typeName = clazz.getSimpleName() + "Impl";
        TypeSpec.Builder typeBuilder = TypeSpec.classBuilder(typeName)
                .addModifiers(Modifier.PUBLIC)
                .addSuperinterface(ClassName.get(clazz));

        for (Map.Entry<String, TypeElement> entry : getFields(clazz).entrySet()) {
            String field = entry.getKey();
            TypeElement type = entry.getValue();

            typeBuilder.addField(FieldSpec.builder(ParameterizedTypeName.get(ClassName.get(Value.class),
                    ClassName.get(type)),
                    field,
                    Modifier.FINAL)
                    .initializer(getInitializer(type))
                    .build());

            String methodSuffix = field.substring(0, 1).toUpperCase() + field.substring(1);

            String getter = "get" + methodSuffix;
            if (hasMethod(clazz, getter, ImmutableList.of())) {
                MethodSpec.Builder constructorBuilder = MethodSpec.methodBuilder(getter)
                        .addModifiers(Modifier.PUBLIC)
                        .returns(ClassName.get(type))
                        .addAnnotation(Override.class)
                        .addStatement("return $L.get()", field);

                typeBuilder.addMethod(constructorBuilder.build());
            }

            String setter = "set" + methodSuffix;
            if (hasMethod(clazz, setter, ImmutableList.of(type))) {
                MethodSpec.Builder constructorBuilder = MethodSpec.methodBuilder(setter)
                        .addModifiers(Modifier.PUBLIC)
                        .addParameter(ClassName.get(type), field)
                        .addAnnotation(Override.class)
                        .addStatement("this.$L.set($L)", field, field);

                typeBuilder.addMethod(constructorBuilder.build());
            }
        }

        return typeBuilder.build();
    }

    private boolean hasMethod(TypeElement clazz, String method, Iterable<TypeElement> args) {
        return Iterables.any(ElementFilter.methodsIn(clazz.getEnclosedElements()),
                (element) -> element.getSimpleName().toString().equals(method)
                        && Iterables.elementsEqual(args,
                        Iterables.transform(element.getParameters(),
                                (param) -> ((DeclaredType) param.asType()).asElement())));
    }

    private String getInitializer(TypeElement value) {
        String className = value.getQualifiedName().toString();
        if (String.class.getName().equals(className)) {
            return JavaValue.class.getName() + (".of(null, org.nibiru.model.core.impl.java.JavaType.STRING)");
        } else {
            return "new " + value.getQualifiedName() + "Value()";
        }
    }
}
