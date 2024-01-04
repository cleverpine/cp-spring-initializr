package com.cleverpine.cpspringinitializr.generator.customizer.maven.plugin;

import com.cleverpine.cpspringinitializr.generator.customizer.maven.MavenCustomizer;
import io.spring.initializr.generator.buildsystem.maven.MavenBuild;
import io.spring.initializr.generator.buildsystem.maven.MavenPlugin;

import static com.cleverpine.cpspringinitializr.logging.TerminalLogger.log;
import static com.cleverpine.cpspringinitializr.logging.TerminalLogger.logMajorStep;

public class MavenCompilerPluginCustomizer extends MavenCustomizer {

    public MavenCompilerPluginCustomizer(int order) {
        super(order);
    }

    @Override
    public void customize(MavenBuild build) {
        // TODO: logging the 'majorStep' here is a work around, think of a way to fix it
        logMajorStep("Customizing pom.xml file...");
        build.plugins().add("org.apache.maven.plugins", "maven-compiler-plugin", this::addPluginMetadata);
        log("[maven-compiler-plugin] added to pom.xml file");
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
