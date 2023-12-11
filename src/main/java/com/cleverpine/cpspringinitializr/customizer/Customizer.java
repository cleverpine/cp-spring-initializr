package com.cleverpine.cpspringinitializr.customizer;

import io.spring.initializr.generator.language.java.JavaTypeDeclaration;
import org.springframework.core.Ordered;

@FunctionalInterface
public interface Customizer extends Ordered {

    void customize(JavaTypeDeclaration typeDeclaration);

    @Override
    default int getOrder() {
        return 0;
    }
}
