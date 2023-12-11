package com.cleverpine.cpspringinitializr.config;

import io.spring.initializr.generator.io.IndentingWriterFactory;
import io.spring.initializr.generator.io.SimpleIndentStrategy;
import io.spring.initializr.generator.project.ProjectDirectoryFactory;
import io.spring.initializr.generator.project.ProjectGenerationConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;

import java.nio.file.Files;
import java.nio.file.Path;

@ProjectGenerationConfiguration
public class InitializrConfiguration {

    @Bean
    @ConditionalOnMissingBean(ProjectDirectoryFactory.class)
    public ProjectDirectoryFactory projectDirectoryFactory() {
        // TODO: extract path value to properties and make it configurable -> implement 'ProjectDirectoryFactory' interface in a class
        return (description) -> Files.createDirectories(Path.of("tmp"));
    }

    @Bean
    @ConditionalOnMissingBean(IndentingWriterFactory.class)
    public IndentingWriterFactory indentingWriterFactory() {
        return IndentingWriterFactory.create(new SimpleIndentStrategy("\t"),
                (builder) -> builder.indentingStrategy("yaml", new SimpleIndentStrategy("  ")));
    }
}
