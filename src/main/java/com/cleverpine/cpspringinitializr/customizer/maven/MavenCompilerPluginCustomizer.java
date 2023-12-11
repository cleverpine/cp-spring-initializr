package com.cleverpine.cpspringinitializr.customizer.maven;

import io.spring.initializr.generator.buildsystem.maven.MavenBuild;
import io.spring.initializr.generator.buildsystem.maven.MavenPlugin;

public class MavenCompilerPluginCustomizer extends MavenCustomizer {

    public MavenCompilerPluginCustomizer(int order) {
        super(order);
    }

    @Override
    public void customize(MavenBuild build) {
        build.plugins().add("org.apache.maven.plugins", "maven-compiler-plugin", this::addPluginMetadata);
    }

    private void addPluginMetadata(MavenPlugin.Builder builder) {
        // TODO: extract version value to a file -> probably in application.yml as a child of 'initializr'
        // TODO: think of a way to make it dynamically updatable -> implement 'UpdateStrategy' class?
        builder.version("3.8.1");
        builder.configuration(this::addConfiguration);
    }

    private void addConfiguration(MavenPlugin.ConfigurationBuilder builder) {
        builder.add("source", "${java.version}");
        builder.add("target", "${java.version}");
    }

}
