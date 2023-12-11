package com.cleverpine.cpspringinitializr.condition;

import com.cleverpine.cpspringinitializr.model.CustomProjectDescription;
import io.spring.initializr.generator.condition.ProjectGenerationCondition;
import io.spring.initializr.generator.project.ProjectDescription;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;

public class OnApiOptionCondition extends ProjectGenerationCondition {

    @Override
    protected boolean matches(ProjectDescription description, ConditionContext context, AnnotatedTypeMetadata metadata) {
        if (description instanceof CustomProjectDescription customDescription) {
            return customDescription.isShouldIncludeApi();
        }
        return false;
    }
}
