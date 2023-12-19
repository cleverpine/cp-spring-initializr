package com.cleverpine.cpspringinitializr.model.yml;

import com.cleverpine.cpspringinitializr.model.yml.spring.SpringProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class YamlConfiguration {

    private SpringProperty spring;

    public YamlConfiguration() {
        this.spring = new SpringProperty();
    }
}