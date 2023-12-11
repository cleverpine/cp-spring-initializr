package com.cleverpine.cpspringinitializr.model;

import io.spring.initializr.generator.project.MutableProjectDescription;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CustomProjectDescription extends MutableProjectDescription {

    private boolean shouldIncludeApi;
}
