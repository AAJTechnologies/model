package org.nibiru.model.gen;

import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.TypeSpec;

import java.io.Writer;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.AnnotationValue;
import javax.lang.model.element.Element;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.ElementFilter;
import javax.tools.JavaFileObject;

@SupportedAnnotationTypes("org.nibiru.model.gen.Generate")
@SupportedSourceVersion(SourceVersion.RELEASE_8)
public class GeneratorProcessor extends AbstractProcessor {
    @Override
    public boolean process(Set<? extends TypeElement> annotations,
                           RoundEnvironment roundEnv) {
//        try {
        TypeMirror generateType = processingEnv.getElementUtils().getTypeElement(
                Generate.class.getName()).asType();
        for (TypeElement element : ElementFilter.typesIn(roundEnv.getElementsAnnotatedWith(Generate.class))) {
            for (AnnotationMirror annotation : element.getAnnotationMirrors()) {
                if (annotation.getAnnotationType().equals(generateType)) {
                    for (Map.Entry<? extends ExecutableElement, ? extends AnnotationValue> entry : annotation.getElementValues().entrySet()) {
                        if ("generators".equals(entry.getKey().getSimpleName().toString())) {
                            AnnotationValue value = entry.getValue();
                            List<AnnotationValue> generatorTypes = (List<AnnotationValue>) value.getValue();
                            for (AnnotationValue typeValue : generatorTypes) {
                                TypeElement generatorType = (TypeElement) ((DeclaredType) typeValue.getValue()).asElement();
                                try {
                                    Generator generator = (Generator) Class.forName(generatorType.getQualifiedName().toString()).newInstance();
                                    TypeSpec typeSpec = generator.generate(element);

                                    JavaFileObject jfo = processingEnv.getFiler().createSourceFile(
                                            element.getEnclosingElement().toString() + "." + typeSpec.name);

                                    try (Writer file =jfo.openWriter()) {
                                        JavaFile.builder(element.getEnclosingElement().toString(), typeSpec)
                                                .build().writeTo(file);
                                        file.flush();
                                    }
                                } catch (Exception e) {
                                    throw new RuntimeException(e);
                                }
                            }
                        }
                    }
                }

            }
        }
        return true;
//        } catch (InstantiationException | IllegalAccessException e) {
//            throw new RuntimeException(e);
//        }
    }
}
