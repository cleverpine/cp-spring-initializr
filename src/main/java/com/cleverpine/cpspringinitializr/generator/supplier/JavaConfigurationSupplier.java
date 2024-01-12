package com.cleverpine.cpspringinitializr.generator.supplier;

import org.springframework.core.Ordered;

@FunctionalInterface
public interface JavaConfigurationSupplier extends Ordered {

    String supply();

    @Override
    default int getOrder() {
        return 0;
    }
}
