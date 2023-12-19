package com.cleverpine.cpspringinitializr.config.resources;

import com.cleverpine.cpspringinitializr.generator.contributor.resources.ApplicationYamlContributor;
import com.cleverpine.cpspringinitializr.generator.contributor.resources.YamlPropertiesContributor;
import com.cleverpine.cpspringinitializr.generator.customizer.yml.YamlPropertiesCustomizer;
import io.spring.initializr.generator.project.ProjectDescription;
import io.spring.initializr.generator.project.ProjectGenerationConfiguration;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.annotation.Bean;

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
        return new YamlPropertiesContributor(projectDescription, customizers);
    }

    @Bean
    public YamlPropertiesCustomizer yamlPropertiesCustomizer() {
        return (yamlConfiguration, applicationName) -> yamlConfiguration
                .getSpring()
                .getApplication()
                .setName(applicationName);
    }
}
