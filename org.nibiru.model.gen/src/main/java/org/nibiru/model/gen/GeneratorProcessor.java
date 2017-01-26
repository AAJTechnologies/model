package org.nibiru.model.gen;

import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;

@SupportedAnnotationTypes("org.nibiru.model.gen.Generate")
@SupportedSourceVersion(SourceVersion.RELEASE_8)
public class GeneratorProcessor extends AbstractProcessor {
    @Override
    public boolean process(Set<? extends TypeElement> annotations,
                           RoundEnvironment roundEnv) {
        try {
        for (Element annotatedElement : roundEnv.getElementsAnnotatedWith(Generate.class)) {
            Generate annotation = annotatedElement.getAnnotation(Generate.class);
            for (Class<? extends Generator> generatorClass :annotation.generators()) {
                    Generator generator = generatorClass.newInstance();
                //generator.generate(annotatedElement.getKind());
            }
        }
        return true;
        } catch (InstantiationException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }
}
