package com.cleverpine.cpspringinitializr.config.maven;

import com.cleverpine.cpspringinitializr.condition.ConditionalOnApiOption;
import com.cleverpine.cpspringinitializr.generator.customizer.maven.dependency.StarterLoggingExclusionCustomizer;
import com.cleverpine.cpspringinitializr.generator.customizer.maven.plugin.MavenCompilerPluginCustomizer;
import com.cleverpine.cpspringinitializr.generator.customizer.maven.plugin.SwaggerCodegenPluginCustomizer;
import com.cleverpine.cpspringinitializr.generator.customizer.maven.profile.ProfileCustomizer;
import io.spring.initializr.generator.condition.ConditionalOnRequestedDependency;
import io.spring.initializr.generator.project.ProjectDescription;
import io.spring.initializr.generator.project.ProjectGenerationConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.annotation.Bean;

@ProjectGenerationConfiguration
public class InitializrMavenConfiguration {

    @Bean
    public MavenCompilerPluginCustomizer mavenCompilerPluginCustomizer() {
        return new MavenCompilerPluginCustomizer(1);
    }

    @Bean
    @ConditionalOnBean(ProjectDescription.class)
    @ConditionalOnApiOption
    public SwaggerCodegenPluginCustomizer swaggerCodegenPluginCustomizer(ProjectDescription projectDescription) {
        return new SwaggerCodegenPluginCustomizer(2, projectDescription);
    }

    @Bean
    @ConditionalOnBean(ProjectDescription.class)
    @ConditionalOnApiOption
    public ProfileCustomizer mavenProfileCustomizer() {
        return new ProfileCustomizer(3);
    }

    @Bean
    @ConditionalOnBean(ProjectDescription.class)
    @ConditionalOnRequestedDependency("cp-logging-library")
    public StarterLoggingExclusionCustomizer starterLoggingExclusionCustomizer() {
        // TODO: Not a fan of 'StarterLoggingExclusionCustomizer' implementation, rethink it
        return new StarterLoggingExclusionCustomizer(4);
    }
}
