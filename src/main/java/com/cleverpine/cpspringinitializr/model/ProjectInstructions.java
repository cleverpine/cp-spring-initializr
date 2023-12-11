package com.cleverpine.cpspringinitializr.model;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class ProjectInstructions {

    private String name;
    private List<String> dependencies = new ArrayList<>();
    private boolean shouldIncludeApi;
}
