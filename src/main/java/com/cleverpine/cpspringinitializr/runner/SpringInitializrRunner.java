package com.cleverpine.cpspringinitializr.runner;

import com.cleverpine.cpspringinitializr.generator.ProjectGenerationInvoker;
import com.cleverpine.cpspringinitializr.model.ProjectInstructions;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Objects;

@Component
@RequiredArgsConstructor
public class SpringInitializrRunner implements ApplicationRunner {

    private final ProjectGenerationInvoker projectGenerationInvoker;

    @Override
    public void run(ApplicationArguments args) {
        var projectInstructions = new ProjectInstructions();
        // TODO: Extraction & mapping should be a separate class
        this.extractName(args, projectInstructions);
        this.extractDependencies(args, projectInstructions);
        this.extractApiOption(args, projectInstructions);

        projectGenerationInvoker.invokeProjectGeneration(projectInstructions);
    }

    private void extractName(ApplicationArguments args, ProjectInstructions projectInstructions) {
        if (!args.containsOption("name")) {
            throw new IllegalArgumentException("Project name must be provided");
        }
        args.getOptionValues("name")
                .stream()
                .filter(Objects::nonNull)
                .findFirst()
                .filter(value -> !value.isEmpty())
                .ifPresent(projectInstructions::setName);
    }

    private void extractDependencies(ApplicationArguments args, ProjectInstructions projectInstructions) {
        if (args.containsOption("dependencies")) {
            var dependencies = args.getOptionValues("dependencies");
            projectInstructions.setDependencies(new ArrayList<>(dependencies));
        }
    }

    private void extractApiOption(ApplicationArguments args, ProjectInstructions projectInstructions) {
        if (args.containsOption("includeApi")) {
            var apiOption = args.getOptionValues("includeApi");
            boolean shouldIncludeApi = apiOption.stream()
                    .map(Boolean::parseBoolean)
                    .findFirst()
                    .orElse(false);
            projectInstructions.setShouldIncludeApi(shouldIncludeApi);
        }
    }
}
