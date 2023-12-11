package com.cleverpine.cpspringinitializr.customizer.maven;

import io.spring.initializr.generator.buildsystem.maven.MavenBuild;
import io.spring.initializr.generator.spring.build.BuildCustomizer;

public abstract class MavenCustomizer implements BuildCustomizer<MavenBuild> {

    private final int order;

    public MavenCustomizer(int order) {
        this.order = order;
    }

    @Override
    public int getOrder() {
        return this.order;
    }
}
