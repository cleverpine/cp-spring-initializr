package com.cleverpine.cpspringinitializr.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProjectInstructions {

    private String name;
    private List<String> dependencies = new ArrayList<>();
    private boolean shouldIncludeApi;

    public void addDependency(String dependency) {
        this.dependencies.add(dependency);
    }
}
