package com.cleverpine.cpspringinitializr.config.resources;

import com.cleverpine.cpspringinitializr.generator.contributor.resources.ApplicationYamlContributor;
import com.cleverpine.cpspringinitializr.generator.contributor.resources.YamlPropertiesContributor;
import com.cleverpine.cpspringinitializr.generator.customizer.yml.YamlPropertiesCustomizer;
import com.cleverpine.cpspringinitializr.model.yml.logging.LoggingProperty;
import com.cleverpine.cpspringinitializr.model.yml.virava.ViravaProperty;
import io.spring.initializr.generator.condition.ConditionalOnRequestedDependency;
import io.spring.initializr.generator.project.ProjectDescription;
import io.spring.initializr.generator.project.ProjectGenerationConfiguration;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.annotation.Bean;

import static com.cleverpine.cpspringinitializr.logging.TerminalLogger.log;

@ProjectGenerationConfiguration
public class InitializrResourcesConfiguration {

    @Bean
    public ApplicationYamlContributor applicationYamlContributor() {
        return new ApplicationYamlContributor();
    }

    @Bean
    @ConditionalOnBean(ProjectDescription.class)
    public YamlPropertiesContributor yamlPropertiesContributor(ProjectDescription projectDescription,
                                                               ObjectProvider<YamlPropertiesCustomizer> customizers) {
        return new YamlPropertiesContributor("src/main/resources/application.yml", projectDescription, customizers);
    }

    @Bean
    public YamlPropertiesCustomizer springPropertyCustomizer() {
        return (yamlConfiguration, applicationName) -> {
            yamlConfiguration
                    .getSpring()
                    .getApplication()
                    .setName(applicationName);
            log("[spring] property added to file");
        };
    }

    @Bean
    @ConditionalOnBean(ProjectDescription.class)
    @ConditionalOnRequestedDependency("cp-virava-spring-helper")
    public YamlPropertiesCustomizer viravaPropertyCustomizer() {
        return (yamlConfiguration, applicationName) -> {
            yamlConfiguration.setAuth(new ViravaProperty());
            log("[auth] property added to file");
        };
    }

    @Bean
    @ConditionalOnBean(ProjectDescription.class)
    @ConditionalOnRequestedDependency("cp-logging-library")
    public YamlPropertiesCustomizer loggingPropertyCustomizer() {
        return (yamlConfiguration, applicationName) -> {
            yamlConfiguration.setLogging(new LoggingProperty());
            yamlConfiguration
                    .getLogging()
                    .getLevel()
                    .setRoot("INFO");
            log("[logging] property added to file");
        };
    }
}
