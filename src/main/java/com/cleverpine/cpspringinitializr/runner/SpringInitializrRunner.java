package com.cleverpine.cpspringinitializr.runner;

import com.cleverpine.cpspringinitializr.generator.ProjectGenerationInvoker;
import com.cleverpine.cpspringinitializr.model.ProjectInstructions;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import static com.cleverpine.cpspringinitializr.logging.TerminalLogger.logError;

@Component
@RequiredArgsConstructor
public class SpringInitializrRunner implements ApplicationRunner {
    private final ProjectGenerationInvoker projectGenerationInvoker;

    @Override
    public void run(ApplicationArguments args) {
        // TODO: Extraction & mapping should be a separate class
        var name = this.extractName(args);
        var dependencies = this.extractDependencies(args);
        var shouldIncludeApi = this.extractApiOption(args);

        var projectInstructions = new ProjectInstructions(name, dependencies, shouldIncludeApi);

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

    private String extractName(ApplicationArguments args) {
        if (!args.containsOption("name")) {
            throw new IllegalArgumentException("Project name must be provided");
        }
        return args.getOptionValues("name")
                .stream()
                .filter(Objects::nonNull)
                .findFirst()
                .filter(value -> !value.isEmpty())
                .orElseThrow(() -> new IllegalArgumentException("Project name must be provided"));
    }

    private List<String> extractDependencies(ApplicationArguments args) {
        var dependencies = new ArrayList<String>();
        if (args.containsOption("dependencies")) {
            var dependenciesArgs = args.getOptionValues("dependencies")
                    .stream()
                    .findFirst()
                    .orElse("");
            if (!dependenciesArgs.isEmpty()) {
                var dependenciesArray = dependenciesArgs.replace(" ", "").split(",");
                dependencies = new ArrayList<>(Arrays.asList(dependenciesArray));
            }
        }
        return dependencies;
    }

    private boolean extractApiOption(ApplicationArguments args) {
        boolean shouldIncludeApi = false;
        if (args.containsOption("includeApi")) {
            var apiOption = args.getOptionValues("includeApi");
            shouldIncludeApi = apiOption.stream()
                    .map(Boolean::parseBoolean)
                    .findFirst()
                    .orElse(false);
        }
        return shouldIncludeApi;
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
