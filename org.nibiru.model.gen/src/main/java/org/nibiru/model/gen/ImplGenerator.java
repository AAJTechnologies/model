package org.nibiru.model.gen;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterizedTypeName;
import com.squareup.javapoet.TypeSpec;

import org.nibiru.model.core.api.Value;

import java.util.Map;

import javax.lang.model.element.Modifier;

public class ImplGenerator extends BaseGenerator {
    @Override
    TypeSpec generateCode(Class<?> clazz) {
        String typeName = clazz.getSimpleName() + "Impl";
        TypeSpec.Builder typeBuilder = TypeSpec.classBuilder(typeName)
                .addModifiers(Modifier.PUBLIC)
                .addSuperinterface(clazz);

        for (Map.Entry<String, Class<?>> entry : getFields(clazz).entrySet()) {
            String field = entry.getKey();
            Class<?> type = entry.getValue();

            typeBuilder.addField(ParameterizedTypeName.get(ClassName.get(Value.class),
                    ClassName.get(type)),
                    field,
                    Modifier.FINAL);

            String methodSuffix = field.substring(0, 1).toUpperCase() + field.substring(1);

            String getter = "get" + methodSuffix;
            if (hasMethod(clazz, getter)) {
                MethodSpec.Builder constructorBuilder = MethodSpec.methodBuilder(getter)
                        .addModifiers(Modifier.PUBLIC)
                        .returns(type)
                        .addAnnotation(Override.class)
                        .addStatement("return $L.get()", field);

                typeBuilder.addMethod(constructorBuilder.build());
            }


            String setter = "set" + methodSuffix;
            if (hasMethod(clazz, setter, type)) {
                MethodSpec.Builder constructorBuilder = MethodSpec.methodBuilder(setter)
                        .addModifiers(Modifier.PUBLIC)
                        .addParameter(type, field)
                        .addAnnotation(Override.class)
                        .addStatement("this.$L.set($L)", field, field);

                typeBuilder.addMethod(constructorBuilder.build());
            }
        }


        return typeBuilder.build();
    }

    private boolean hasMethod(Class<?> clazz, String method, Class<?>... args) {
        try {
            clazz.getMethod(method, args);
            return true;
        } catch (NoSuchMethodException e) {
            return false;
        }
    }
}
