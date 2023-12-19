package com.cleverpine.cpspringinitializr.generator.customizer.java;

import io.spring.initializr.generator.language.java.JavaTypeDeclaration;
import org.springframework.core.Ordered;

@FunctionalInterface
public interface JavaConfigurationCustomizer extends Ordered {

    void customize(JavaTypeDeclaration typeDeclaration, String basePackage);

    @Override
    default int getOrder() {
        return 0;
    }
}
