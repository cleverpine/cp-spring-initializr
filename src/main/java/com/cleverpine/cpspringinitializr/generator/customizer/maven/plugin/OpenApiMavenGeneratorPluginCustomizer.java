package com.cleverpine.cpspringinitializr.generator.customizer.maven.plugin;

import com.cleverpine.cpspringinitializr.generator.customizer.maven.MavenCustomizer;
import io.spring.initializr.generator.buildsystem.maven.MavenBuild;
import io.spring.initializr.generator.buildsystem.maven.MavenPlugin;
import io.spring.initializr.generator.project.ProjectDescription;


import static com.cleverpine.cpspringinitializr.logging.TerminalLogger.log;

public class OpenApiMavenGeneratorPluginCustomizer extends MavenCustomizer {

    public static final String OPENAPI_GENERATOR_MAVEN_PLUGIN_ARTIFACT = "openapi-generator-maven-plugin";
    public static final String OPENAPI_GENERATOR_MAVEN_PLUGIN_GROUPID = "org.openapitools";
    private final ProjectDescription projectDescription;

    public OpenApiMavenGeneratorPluginCustomizer(int order, ProjectDescription projectDescription) {
        super(order);
        this.projectDescription = projectDescription;
    }

    @Override
    public void customize(MavenBuild build) {
        this.addBuildPlugin(build);
        log("[{}] added to pom.xml file", OPENAPI_GENERATOR_MAVEN_PLUGIN_ARTIFACT);
    }

    private void addBuildPlugin(MavenBuild build) {
        build.plugins().add(OPENAPI_GENERATOR_MAVEN_PLUGIN_GROUPID, OPENAPI_GENERATOR_MAVEN_PLUGIN_ARTIFACT, this::addPluginMetadata);
    }

    private void addPluginMetadata(MavenPlugin.Builder builder) {
        // TODO: extract version value to a file -> probably in application.yml as a child of 'initializr'
        // TODO: think of a way to make it dynamically updatable -> implement 'UpdateStrategy' class?
        builder.version("7.2.0");
        builder.execution("generate-api", this::addExecution);
    }

    private void addExecution(MavenPlugin.ExecutionBuilder builder) {
        builder.goal("generate");
        builder.configuration(this::addConfiguration);
    }

    private void addConfiguration(MavenPlugin.ConfigurationBuilder builder) {
        var packageName = projectDescription.getPackageName();
        builder.add("auth", "${api.specification.authorization}");
        builder.add("inputSpec", "${api.query.specification.url}");
        builder.add("generatorName", "spring");
        builder.add("apiPackage",  packageName + ".api");
        builder.add("modelPackage", packageName + ".model");
        builder.add("output", "${project.build.directory}/generated-sources");
        builder.add("generateSupportingFiles", "true");
        builder.add("generateModelTests", "false");
        builder.add("generateApiTests", "false");
        builder.add("configOptions", this::addConfigurationOptions);
    }

    private void addConfigurationOptions(MavenPlugin.ConfigurationBuilder builder) {
        builder.add("useTags", "true");
        builder.add("excludeTests", "true");
        builder.add("interfaceOnly", "true");
        builder.add("useSpringBoot3", "true");
        builder.add("useJakartaEe", "true");
    }
}