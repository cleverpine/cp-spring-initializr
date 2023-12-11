package com.cleverpine.cpspringinitializr.generation.contributor;

import io.spring.initializr.generator.project.contributor.SingleResourceProjectContributor;

public class ApplicationYamlContributor extends SingleResourceProjectContributor {

    public ApplicationYamlContributor() {
        super("src/main/resources/application.yml", "classpath:configuration/application.yml");
    }
}