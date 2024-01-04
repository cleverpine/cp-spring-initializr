package com.cleverpine.cpspringinitializr.runner;

import com.cleverpine.cpspringinitializr.generator.ProjectGenerationInvoker;
import com.cleverpine.cpspringinitializr.model.ProjectInstructions;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

import static com.cleverpine.cpspringinitializr.logging.TerminalLogger.logError;

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

        try {
            projectGenerationInvoker.invokeProjectGeneration(projectInstructions);
        } catch (Exception exception) {
            var isVerboseEnabled = this.extractVerboseOption(args);
            if (isVerboseEnabled) {
                exception.printStackTrace();
            } else {
                logError(exception.getMessage());
            }
            System.exit(1);
        }
    }

    private void extractName(ApplicationArguments args, ProjectInstructions projectInstructions) {
        if (!args.containsOption("name")) {
            throw new IllegalArgumentException("Project name must be provided");
        }
        var name = args.getOptionValues("name")
                .stream()
                .filter(Objects::nonNull)
                .findFirst()
                .filter(value -> !value.isEmpty())
                .orElseThrow(() -> new IllegalArgumentException("Project name must be provided1"));
        projectInstructions.setName(name);
    }

    private void extractDependencies(ApplicationArguments args, ProjectInstructions projectInstructions) {
        if (args.containsOption("dependencies")) {
            var dependenciesArgs = args.getOptionValues("dependencies")
                    .stream()
                    .findFirst()
                    .orElse("");
            if (dependenciesArgs.isEmpty()) {
                return;
            }
            var dependenciesArray = dependenciesArgs.split(",");
            var dependencies = Arrays.asList(dependenciesArray);
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

    private boolean extractVerboseOption(ApplicationArguments args) {
        if (!args.containsOption("verbose")) {
            return false;
        }
        return args.getOptionValues("verbose")
                .stream()
                .map(Boolean::parseBoolean)
                .findFirst()
                .orElse(false);
    }
}
