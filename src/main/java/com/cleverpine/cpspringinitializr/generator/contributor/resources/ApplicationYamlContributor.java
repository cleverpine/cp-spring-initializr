package com.cleverpine.cpspringinitializr.generator.contributor.resources;

import io.spring.initializr.generator.project.contributor.SingleResourceProjectContributor;

public class ApplicationYamlContributor extends SingleResourceProjectContributor {

    public ApplicationYamlContributor() {
        super("src/main/resources/application.yml", "classpath:configuration/application.yml");
    }
}