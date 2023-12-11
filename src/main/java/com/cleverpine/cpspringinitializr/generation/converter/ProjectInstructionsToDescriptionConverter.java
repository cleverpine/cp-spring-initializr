package com.cleverpine.cpspringinitializr.generation.converter;

import com.cleverpine.cpspringinitializr.model.CustomProjectDescription;
import com.cleverpine.cpspringinitializr.model.ProjectInstructions;
import io.spring.initializr.generator.buildsystem.BuildSystem;
import io.spring.initializr.generator.buildsystem.maven.MavenBuildSystem;
import io.spring.initializr.generator.language.Language;
import io.spring.initializr.generator.packaging.Packaging;
import io.spring.initializr.generator.project.ProjectDescription;
import io.spring.initializr.generator.version.Version;
import io.spring.initializr.metadata.Dependency;
import io.spring.initializr.metadata.InitializrMetadata;
import io.spring.initializr.metadata.support.MetadataBuildItemMapper;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ProjectInstructionsToDescriptionConverter {

    private static final String MAIN_CLASS_APPLICATION_SUFFIX = "Application";
    private static final String HYPHEN_DELIMITER = "-";
    private static final String PERIOD_DELIMITER = ".";

    public ProjectDescription convertWithDefaultMetadata(ProjectInstructions instructions, InitializrMetadata metadata) {
        // TODO: add validations if necessary -> see 'DefaultProjectRequestToDescriptionConverter' in 'initializr-web' module
        var description = new CustomProjectDescription();
        var name = instructions.getName();
        var platformVersion = this.getDefaultSpringBootVersion(metadata);
        var resolvedDependencies = this.getResolvedDependencies(instructions.getDependencies(), platformVersion, metadata);
        var groupId = this.getDefaultGroupId(metadata);

        description.setName(name);
        description.setPlatformVersion(platformVersion);
        description.setBuildSystem(this.getMavenBuildSystem());
        description.setPackaging(this.getDefaultPackaging(metadata));
        description.setLanguage(this.getDefaultLanguage(metadata));
        description.setGroupId(groupId);
        description.setArtifactId(name);
        description.setVersion(this.getDefaultVersion(metadata));
        description.setApplicationName(this.getApplicationName(name));
        description.setPackageName(this.getPackageName(groupId, name));
        description.setBaseDirectory(name);
        resolvedDependencies.forEach((dependency) -> description.addDependency(dependency.getId(),
                MetadataBuildItemMapper.toDependency(dependency)));
        description.setShouldIncludeApi(instructions.isShouldIncludeApi());

        return description;
    }

    private Version getDefaultSpringBootVersion(InitializrMetadata metadata) {
        var defaultSpringVersion = metadata.getBootVersions().getDefault().getId();
        return Version.parse(defaultSpringVersion);
    }

    private List<Dependency> getResolvedDependencies(List<String> dependencies, Version platformVersion,
                                                     InitializrMetadata metadata) {
        return dependencies.stream().map((it) -> {
            var dependency = metadata.getDependencies().get(it);
            return dependency.resolve(platformVersion);
        }).collect(Collectors.toList());
    }

    private BuildSystem getMavenBuildSystem() {
        return BuildSystem.forId(MavenBuildSystem.ID);
    }

    private Packaging getDefaultPackaging(InitializrMetadata metadata) {
        var defaultPackagingId = metadata.getPackagings().getDefault().getId();
        return Packaging.forId(defaultPackagingId);
    }

    private Language getDefaultLanguage(InitializrMetadata metadata) {
        var language = metadata.getLanguages().getDefault().getId();
        var languageVersion = metadata.getJavaVersions().getDefault().getId();
        return Language.forId(language, languageVersion);
    }

    private String getDefaultGroupId(InitializrMetadata metadata) {
        return metadata.getGroupId().getContent();
    }

    private String getDefaultVersion(InitializrMetadata metadata) {
        return metadata.getVersion().getContent();
    }

    private String getApplicationName(String name) {
        if (name == null || name.isEmpty()) {
            return name;
        }

        String[] parts = name.split(HYPHEN_DELIMITER);
        var sb = new StringBuilder();

        for (String part : parts) {
            sb.append(StringUtils.capitalize(part));
        }

        return sb.append(MAIN_CLASS_APPLICATION_SUFFIX).toString();
    }

    private String getPackageName(String groupId, String name) {
        return (groupId + PERIOD_DELIMITER + name).replaceAll(HYPHEN_DELIMITER, "");
    }
}
