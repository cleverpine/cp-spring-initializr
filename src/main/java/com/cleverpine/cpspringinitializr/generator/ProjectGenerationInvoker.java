package com.cleverpine.cpspringinitializr.generator;

import com.cleverpine.cpspringinitializr.generator.converter.ProjectInstructionsToDescriptionConverter;
import com.cleverpine.cpspringinitializr.model.ProjectInstructions;
import io.spring.initializr.generator.project.DefaultProjectAssetGenerator;
import io.spring.initializr.generator.project.ProjectAssetGenerator;
import io.spring.initializr.generator.project.ProjectGenerationContext;
import io.spring.initializr.generator.project.ProjectGenerator;
import io.spring.initializr.metadata.InitializrMetadata;

import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.nio.file.Path;

@Component
@RequiredArgsConstructor
public class ProjectGenerationInvoker {

    private final ApplicationContext applicationContext;
    private final InitializrMetadata metadata;
    private final ProjectInstructionsToDescriptionConverter converter;
    private final ProjectAssetGenerator<Path> projectAssetGenerator = new DefaultProjectAssetGenerator();

    public void invokeProjectGeneration(ProjectInstructions projectInstructions) {
        var projectDescription = this.converter.convertWithDefaultMetadata(projectInstructions, metadata);

        var projectGenerator = this.initializeProjectGenerator();
        // TODO: handle duplicate directory case -> probably implement own 'ProjectAssetGenerator<Path>' and do it there
        projectGenerator.generate(projectDescription, projectAssetGenerator);
    }

    private ProjectGenerator initializeProjectGenerator() {
        return new ProjectGenerator(this::customizeProjectGenerationContext);
    }

    private void customizeProjectGenerationContext(ProjectGenerationContext projectGenerationContext) {
        // TODO: extract beans to be removed to a file
        projectGenerationContext.setParent(this.applicationContext);
        projectGenerationContext.removeBeanDefinition("io.spring.initializr.generator.spring.build.gradle.GradleProjectGenerationConfiguration");
        projectGenerationContext.removeBeanDefinition("io.spring.initializr.generator.spring.code.groovy.GroovyProjectGenerationConfiguration");
        projectGenerationContext.removeBeanDefinition("io.spring.initializr.generator.spring.code.kotlin.KotlinProjectGenerationConfiguration");
        projectGenerationContext.removeBeanDefinition("io.spring.initializr.generator.spring.configuration.ApplicationConfigurationProjectGenerationConfiguration");
    }
}
