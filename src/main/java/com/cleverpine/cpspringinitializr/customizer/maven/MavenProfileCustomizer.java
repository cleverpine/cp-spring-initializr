package com.cleverpine.cpspringinitializr.customizer.maven;

import io.spring.initializr.generator.buildsystem.maven.MavenBuild;
import io.spring.initializr.generator.buildsystem.maven.MavenProfile;
public class MavenProfileCustomizer extends MavenCustomizer {

    public MavenProfileCustomizer(int order) {
        super(order);
    }

    @Override
    public void customize(MavenBuild build) {
        var profile = build.profiles().id("local");
        this.setProfileAsDefault(profile);
        this.addProfileProperties(profile);
    }

    private void setProfileAsDefault(MavenProfile profile) {
        profile.activation().activeByDefault(true);
    }

    private void addProfileProperties(MavenProfile profile) {
        profile.properties()
                .property("api.specification.authorization", "<!-- TODO: Insert your authorization token here! -->")
                .property("api.query.specification.url", "<-- TODO: Insert your API specification URL here! -->");
    }
}
