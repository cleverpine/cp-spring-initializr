package com.cleverpine.cpspringinitializr.generator.customizer.maven.dependency;

import com.cleverpine.cpspringinitializr.generator.customizer.maven.MavenCustomizer;
import io.spring.initializr.generator.buildsystem.Dependency;
import io.spring.initializr.generator.buildsystem.maven.MavenBuild;
import io.spring.initializr.metadata.InitializrMetadata;

import java.util.Collections;
import java.util.List;

public class AnnotationProcessorExclusionBuildCustomizer extends MavenCustomizer {

    private static final List<String> KNOWN_ANNOTATION_PROCESSORS = Collections
            .singletonList("configuration-processor");

    private final InitializrMetadata metadata;

    public AnnotationProcessorExclusionBuildCustomizer(int order, InitializrMetadata metadata) {
        super(order);
        this.metadata = metadata;
    }

    @Override
    public void customize(MavenBuild build) {
        if (!build.plugins().has("org.springframework.boot", "spring-boot-maven-plugin")) {
            return;
        }

        List<Dependency> dependencies = build.dependencies()
                .ids()
                .filter(this::isAnnotationProcessor)
                .filter((id) -> !KNOWN_ANNOTATION_PROCESSORS.contains(id))
                .map((id) -> build.dependencies().get(id))
                .toList();
        if (!dependencies.isEmpty()) {
            build.plugins()
                    .add("org.springframework.boot", "spring-boot-maven-plugin", (plugin) -> plugin
                            .configuration((configuration) -> configuration.configure("excludes", (excludes) -> {
                                for (io.spring.initializr.generator.buildsystem.Dependency dependency : dependencies) {
                                    excludes.add("exclude", (exclude) -> {
                                        exclude.add("groupId", dependency.getGroupId());
                                        exclude.add("artifactId", dependency.getArtifactId());
                                    });
                                }
                            })));
        }
    }

    private boolean isAnnotationProcessor(String id) {
        io.spring.initializr.metadata.Dependency dependency = this.metadata.getDependencies().get(id);
        return (dependency != null) && io.spring.initializr.metadata.Dependency.SCOPE_ANNOTATION_PROCESSOR.equals(dependency.getScope());
    }
}
