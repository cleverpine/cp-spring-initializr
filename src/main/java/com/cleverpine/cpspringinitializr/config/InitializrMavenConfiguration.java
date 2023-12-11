package com.cleverpine.cpspringinitializr.config;

import com.cleverpine.cpspringinitializr.condition.ConditionalOnApiOption;
import com.cleverpine.cpspringinitializr.customizer.maven.MavenCompilerPluginCustomizer;
import com.cleverpine.cpspringinitializr.customizer.maven.MavenProfileCustomizer;
import com.cleverpine.cpspringinitializr.customizer.maven.MavenSwaggerCodegenPluginCustomizer;
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
    public MavenSwaggerCodegenPluginCustomizer mavenSwaggerCodegenPluginCustomizer(ProjectDescription projectDescription) {
        return new MavenSwaggerCodegenPluginCustomizer(2, projectDescription);
    }

    @Bean
    @ConditionalOnBean(ProjectDescription.class)
    @ConditionalOnApiOption
    public MavenProfileCustomizer mavenProfileCustomizer() {
        return new MavenProfileCustomizer(3);
    }
}
