package com.cleverpine.cpspringinitializr.config.instruction;

import com.cleverpine.cpspringinitializr.generator.customizer.ProjectInstructionsCustomizer;
import io.spring.initializr.generator.condition.ConditionalOnRequestedDependency;
import io.spring.initializr.generator.project.ProjectDescription;
import io.spring.initializr.generator.project.ProjectGenerationConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.annotation.Bean;

@ProjectGenerationConfiguration
public class ProjectInstructionsConfig {

    @Bean
    public ProjectInstructionsCustomizer defaultDependenciesCustomizer() {
        return instructions -> {
            instructions.addDependency("web");
            instructions.addDependency("lombok");
            instructions.addDependency("jackson-databind");
        };
    }

    @Bean
    @ConditionalOnBean(ProjectDescription.class)
    @ConditionalOnRequestedDependency("cp-logging-library")
    public ProjectInstructionsCustomizer cpLoggingProvidedDependenciesCustomizer() {
        return instructions -> {
            instructions.addDependency("aop");
            instructions.addDependency("log4j2");
        };
    }
}
