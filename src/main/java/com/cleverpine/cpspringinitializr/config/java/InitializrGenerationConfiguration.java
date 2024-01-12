package com.cleverpine.cpspringinitializr.config.java;

import com.cleverpine.cpspringinitializr.generator.contributor.java.JavaConfigurationContributor;
import com.cleverpine.cpspringinitializr.generator.supplier.JavaConfigurationSupplier;
import io.spring.initializr.generator.condition.ConditionalOnRequestedDependency;
import io.spring.initializr.generator.project.ProjectDescription;
import io.spring.initializr.generator.project.ProjectGenerationConfiguration;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.annotation.Bean;

@ProjectGenerationConfiguration
public class InitializrGenerationConfiguration {

    @Bean
    @ConditionalOnBean(ProjectDescription.class)
    public JavaConfigurationContributor asd(ProjectDescription projectDescription, ObjectProvider<JavaConfigurationSupplier> suppliers) {
        return new JavaConfigurationContributor("src/main/java/com/cleverpine/", projectDescription, suppliers);
    }

    @Bean
    @ConditionalOnBean(ProjectDescription.class)
    @ConditionalOnRequestedDependency("cp-virava-spring-helper")
    public JavaConfigurationSupplier viravaConfigurationSupplier() {
        return () -> "classpath:configuration/java/virava";
    }

    @Bean
    @ConditionalOnBean(ProjectDescription.class)
    @ConditionalOnRequestedDependency("cp-logging-library")
    public JavaConfigurationSupplier loggingConfigurationSupplier() {
        return () -> "classpath:configuration/java/logging";
    }
}
