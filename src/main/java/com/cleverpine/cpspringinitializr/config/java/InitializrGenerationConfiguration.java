package com.cleverpine.cpspringinitializr.config.java;

import com.cleverpine.cpspringinitializr.generator.contributor.java.JavaConfigurationContributor;
import com.cleverpine.cpspringinitializr.generator.customizer.java.JavaConfigurationCustomizer;
import io.spring.initializr.generator.condition.ConditionalOnRequestedDependency;
import io.spring.initializr.generator.io.IndentingWriterFactory;
import io.spring.initializr.generator.language.ClassName;
import io.spring.initializr.generator.language.java.JavaSourceCodeWriter;
import io.spring.initializr.generator.project.ProjectDescription;
import io.spring.initializr.generator.project.ProjectGenerationConfiguration;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.annotation.Bean;

import java.lang.reflect.Modifier;

import static com.cleverpine.cpspringinitializr.logging.TerminalLogger.log;

@ProjectGenerationConfiguration
public class InitializrGenerationConfiguration {

    @Bean
    @ConditionalOnBean(ProjectDescription.class)
    @ConditionalOnRequestedDependency("cp-logging-library")
    public JavaConfigurationCustomizer javaConfigurationCustomizer() {
        // TODO: refactor this to be more generic/reusable for other libraries
        return (javaTypeDeclaration, basePackage) -> {
            javaTypeDeclaration.modifiers(Modifier.PUBLIC);
            javaTypeDeclaration.annotations().add(ClassName.of("com.cleverpine.springlogginglibrary.aop.EnableCPLogging"));
            javaTypeDeclaration.annotations().add(ClassName.of("org.springframework.context.annotation.ComponentScan"),
                    (annotation) -> annotation.add("basePackages", "com.cleverpine.springlogginglibrary.*", basePackage));
            log("{} file added to project", javaTypeDeclaration.getName());
        };
    }

    @Bean
    @ConditionalOnBean(ProjectDescription.class)
    @ConditionalOnRequestedDependency("cp-logging-library")
    public JavaConfigurationContributor javaConfigurationContributor(IndentingWriterFactory indentingWriterFactory,
                                                                     ProjectDescription projectDescription,
                                                                     ObjectProvider<JavaConfigurationCustomizer> customizers) {
        // TODO: refactor this to be more generic/reusable for other libraries
        return new JavaConfigurationContributor(new JavaSourceCodeWriter(indentingWriterFactory), projectDescription, customizers);
    }
}
