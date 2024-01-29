package com.cleverpine.cpspringinitializr.generator;

import com.cleverpine.cpspringinitializr.generator.customizer.ProjectInstructionsCustomizer;
import com.cleverpine.cpspringinitializr.model.ProjectInstructions;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.util.LambdaSafe;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class ProjectInstructionsGenerator {

    private final ObjectProvider<ProjectInstructionsCustomizer> customizers;

    public ProjectInstructions generateInstructions(String name, List<String> dependencies, boolean shouldIncludeApi) {
        var projectInstructions = new ProjectInstructions(name, dependencies, shouldIncludeApi);
        this.customize(projectInstructions);
        return projectInstructions;
    }

    private void customize(ProjectInstructions projectInstructions) {
        List<ProjectInstructionsCustomizer> projectInstructionsCustomizers = this.customizers.orderedStream().toList();
        LambdaSafe.callbacks(ProjectInstructionsCustomizer.class, projectInstructionsCustomizers, projectInstructions)
                .invoke((projectInstructionsCustomizer) -> projectInstructionsCustomizer.customize(projectInstructions));
    }
}
