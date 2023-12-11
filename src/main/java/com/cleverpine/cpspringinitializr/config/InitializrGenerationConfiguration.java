package com.cleverpine.cpspringinitializr.config;

import com.cleverpine.cpspringinitializr.customizer.Customizer;
import com.cleverpine.cpspringinitializr.generation.contributor.ApplicationYamlContributor;
import com.cleverpine.cpspringinitializr.generation.contributor.Contributor;
import io.spring.initializr.generator.condition.ConditionalOnRequestedDependency;
import io.spring.initializr.generator.io.IndentingWriterFactory;
import io.spring.initializr.generator.language.ClassName;
import io.spring.initializr.generator.language.java.JavaSourceCode;
import io.spring.initializr.generator.language.java.JavaSourceCodeWriter;
import io.spring.initializr.generator.project.ProjectDescription;
import io.spring.initializr.generator.project.ProjectGenerationConfiguration;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

import java.lang.reflect.Modifier;
@ComponentScan
@ProjectGenerationConfiguration
public class InitializrGenerationConfiguration {

    // TODO: add conditional annotations

    @Bean
    public ApplicationYamlContributor applicationYamlContributor() {
        return new ApplicationYamlContributor();
    }

    @Bean
    @ConditionalOnBean(Contributor.class)
    public Customizer customizer() {
        // TODO: add missing code declaration
        return (typeDeclaration) -> {
            typeDeclaration.modifiers(Modifier.PUBLIC);
            typeDeclaration.annotations().add(ClassName.of("org.springframework.context.annotation.ComponentScan"));
        };
    }

    @Bean
    @ConditionalOnBean(ProjectDescription.class)
    @ConditionalOnRequestedDependency("cp-logging-library")
    public Contributor contributor(IndentingWriterFactory indentingWriterFactory, ObjectProvider<Customizer> contributor) {
        // TODO: finish
        return new Contributor(new JavaSourceCode(), new JavaSourceCodeWriter(indentingWriterFactory), contributor);
    }
}
