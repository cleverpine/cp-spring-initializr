package com.cleverpine.cpspringinitializr.model.yml.spring;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SpringProperty {
    private ApplicationProperty application;

    public SpringProperty() {
        this.application = new ApplicationProperty();
    }

    @Getter
    @Setter
    public static class ApplicationProperty {
        private String name;

        public ApplicationProperty() {
            this.name = "";
        }
    }
}