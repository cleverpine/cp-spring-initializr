package com.cleverpine.cpspringinitializr.runner;

import com.cleverpine.cpspringinitializr.generation.ProjectGenerationInvoker;
import com.cleverpine.cpspringinitializr.model.ProjectInstructions;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class SpringInitializrRunner implements ApplicationRunner {

    private final ProjectGenerationInvoker projectGenerationInvoker;

    @Override
    public void run(ApplicationArguments args) {
//        var name = args.getOptionValues("name")
//                .stream()
//                .findFirst()
//                .orElseThrow(() -> new IllegalArgumentException("Argument --name is required for the project name"));
        //var name = UUID.randomUUID().toString();
        var name = "mando";
        var projectInstructions = new ProjectInstructions();
        projectInstructions.setName(name);
        projectInstructions.setShouldIncludeApi(true);
        var dependencies = List.of("cp-logging-library", "aop", "log4j2");
        //projectInstructions.setDependencies(dependencies);

        if (args.containsOption("dependencies")) {
            //var dependencies = args.getOptionValues("dependencies");
            projectInstructions.setDependencies(dependencies);
        }

        if (args.containsOption("includeApi")) {
            var apiOption = args.getOptionValues("includeApi");
            boolean shouldIncludeApi = apiOption.stream()
                    .map(Boolean::parseBoolean)
                    .findFirst()
                    .orElse(false);
            projectInstructions.setShouldIncludeApi(shouldIncludeApi);
        }

        projectGenerationInvoker.invokeProjectGeneration(projectInstructions);
    }
}
