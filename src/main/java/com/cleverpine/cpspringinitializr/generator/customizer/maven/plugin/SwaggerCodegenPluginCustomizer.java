package com.cleverpine.cpspringinitializr.generator.customizer.maven.plugin;

import com.cleverpine.cpspringinitializr.generator.customizer.maven.MavenCustomizer;
import io.spring.initializr.generator.buildsystem.maven.MavenBuild;
import io.spring.initializr.generator.buildsystem.maven.MavenPlugin;
import io.spring.initializr.generator.project.ProjectDescription;

public class SwaggerCodegenPluginCustomizer extends MavenCustomizer {

    private final ProjectDescription projectDescription;

    public SwaggerCodegenPluginCustomizer(int order, ProjectDescription projectDescription) {
        super(order);
        this.projectDescription = projectDescription;
    }

    @Override
    public void customize(MavenBuild build) {
        this.addBuildPlugin(build);
    }

    private void addBuildPlugin(MavenBuild build) {
        build.plugins().add("io.swagger.codegen.v3", "swagger-codegen-maven-plugin", this::addPluginMetadata);
    }

    private void addPluginMetadata(MavenPlugin.Builder builder) {
        // TODO: extract version value to a file -> probably in application.yml as a child of 'initializr'
        // TODO: think of a way to make it dynamically updatable -> implement 'UpdateStrategy' class?
        builder.version("3.0.51");
        builder.execution("generate-api", this::addExecution);
    }

    private void addExecution(MavenPlugin.ExecutionBuilder builder) {
        builder.goal("generate");
        builder.configuration(this::addConfiguration);
    }

    private void addConfiguration(MavenPlugin.ConfigurationBuilder builder) {
        var packageName = projectDescription.getPackageName();
        builder.add("language", "java");
        builder.add("auth", "${api.specification.authorization}");
        builder.add("inputSpec", "${api.query.specification.url}");
        builder.add("apiPackage",  packageName + ".api");
        builder.add("modelPackage", packageName + ".model");
        builder.add("modelNameSuffix", "ApiModel");
        builder.add("configOptions", this::addConfigurationOptions);
    }

    private void addConfigurationOptions(MavenPlugin.ConfigurationBuilder builder) {
        builder.add("useTags", "true");
        builder.add("excludeTests", "true");
        builder.add("interfaceOnly", "true");
        builder.add("useSpringBoot3", "true");
    }
}
