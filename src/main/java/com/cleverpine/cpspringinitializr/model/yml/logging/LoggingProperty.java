package com.cleverpine.cpspringinitializr.model.yml.logging;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoggingProperty {

    private LevelProperty level;

    public LoggingProperty() {
        this.level = new LevelProperty();
    }

    @Getter
    @Setter
    public static class LevelProperty {
        private String root;

        public LevelProperty() {
            this.root = "";
        }
    }
}
