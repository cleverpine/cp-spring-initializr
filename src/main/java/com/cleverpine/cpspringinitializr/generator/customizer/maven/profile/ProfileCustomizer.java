package com.cleverpine.cpspringinitializr.generator.customizer.maven.profile;

import com.cleverpine.cpspringinitializr.generator.customizer.maven.MavenCustomizer;
import io.spring.initializr.generator.buildsystem.maven.MavenBuild;
import io.spring.initializr.generator.buildsystem.maven.MavenProfile;

import static com.cleverpine.cpspringinitializr.logging.TerminalLogger.log;

public class ProfileCustomizer extends MavenCustomizer {

    public ProfileCustomizer(int order) {
        super(order);
    }

    @Override
    public void customize(MavenBuild build) {
        var profile = build.profiles().id("local");
        this.setProfileAsDefault(profile);
        this.addProfileProperties(profile);
        log("[profile] added to pom.xml file");
    }

    private void setProfileAsDefault(MavenProfile profile) {
        profile.activation().activeByDefault(true);
    }

    private void addProfileProperties(MavenProfile profile) {
        profile.properties()
                .property("api.query.specification.url", "../${project.artifactId}-api/${project.artifactId}-api.yml")
                .property("api.specification.authorization", "<!-- TODO: Insert your authorization token here! -->");
    }
}
