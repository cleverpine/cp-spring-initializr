package com.cleverpine.cpspringinitializr.generator.customizer.maven.dependency;

import com.cleverpine.cpspringinitializr.generator.customizer.maven.MavenCustomizer;
import io.spring.initializr.generator.buildsystem.Dependency;
import io.spring.initializr.generator.buildsystem.maven.MavenBuild;


public class StarterLoggingExclusionCustomizer extends MavenCustomizer {

    static final String DEFAULT_STARTER = "root_starter";

    public StarterLoggingExclusionCustomizer(int order) {
        super(order);
    }

    @Override
    public void customize(MavenBuild build) {
        // TODO: Think of a way to NOT add the 'spring-boot-starter' dependency here,
        //  but instead utilize 'DefaultStarterBuildCustomizer' class that already adds it, but with lowest precedence
        build.dependencies().add(DEFAULT_STARTER,
                Dependency.withCoordinates("org.springframework.boot", "spring-boot-starter")
                        .exclusions(new Dependency.Exclusion("org.springframework.boot", "spring-boot-starter-logging")));
    }
}
