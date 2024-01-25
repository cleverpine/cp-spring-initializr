package com.cleverpine.cpspringinitializr.model.yml;

import com.cleverpine.cpspringinitializr.model.yml.logging.LoggingProperty;
import com.cleverpine.cpspringinitializr.model.yml.spring.SpringProperty;
import com.cleverpine.cpspringinitializr.model.yml.virava.ViravaProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class YamlConfiguration {

    private SpringProperty spring;
    private ViravaProperty auth;
    private LoggingProperty logging;

    public YamlConfiguration() {
        this.spring = new SpringProperty();
        this.auth = null;
        this.logging = null;
    }
}