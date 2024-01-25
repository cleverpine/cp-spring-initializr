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
    public JavaConfigurationContributor javaConfigurationContributor(ProjectDescription projectDescription, ObjectProvider<JavaConfigurationSupplier> suppliers) {
        return new JavaConfigurationContributor("src/main/java/com/cleverpine/", projectDescription, suppliers);
    }

    @Bean
    @ConditionalOnBean(ProjectDescription.class)
    public JavaConfigurationSupplier configurationSupplier() {
        return () -> "classpath:configuration/java/base";
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

    @Bean
    @ConditionalOnBean(ProjectDescription.class)
    @ConditionalOnRequestedDependency("cp-jpa-specification-resolver")
    public JavaConfigurationSupplier jpaSpecificationConfigurationSupplier() {
        return () -> "classpath:configuration/java/jpaspecification";
    }

    @Bean
    @ConditionalOnBean(ProjectDescription.class)
    @ConditionalOnRequestedDependency("cp-spring-error-util")
    public JavaConfigurationSupplier errorUtilConfigurationSupplier() {
        return () -> "classpath:configuration/java/errorutil";
    }
}
