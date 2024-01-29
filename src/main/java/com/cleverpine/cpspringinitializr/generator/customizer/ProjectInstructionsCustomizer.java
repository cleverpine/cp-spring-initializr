package com.cleverpine.cpspringinitializr.generator.customizer;

import com.cleverpine.cpspringinitializr.model.ProjectInstructions;
import org.springframework.core.Ordered;

@FunctionalInterface
public interface ProjectInstructionsCustomizer extends Ordered {

    void customize(ProjectInstructions instructions);

    @Override
    default int getOrder() {
        return 0;
    }
}
