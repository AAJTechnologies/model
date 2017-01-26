package org.nibiru.model.gen;

import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.TypeElement;

@SupportedAnnotationTypes("org.nibiru.model.gen.GenerateType")
@SupportedSourceVersion(SourceVersion.RELEASE_8)
public class TypeProcessor extends AbstractProcessor {
    private final TypeGenerator generator;

    public TypeProcessor() {
        generator = new TypeGenerator();
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations,
                           RoundEnvironment roundEnv) {
        return true;
    }
}
