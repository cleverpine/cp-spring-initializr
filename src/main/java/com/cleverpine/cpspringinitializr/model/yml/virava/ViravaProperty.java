package com.cleverpine.cpspringinitializr.model.yml.virava;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ViravaProperty {

    private ViravaTokenProperty token;

    public ViravaProperty() {
        this.token = new ViravaTokenProperty();
    }

    @Getter
    @Setter
    public static class ViravaTokenProperty {
        private String usernamePath;
        private String rolesPath;
        private String jwkSetUrl;

        public ViravaTokenProperty() {
            this.usernamePath = "TODO: replace with your username path";
            this.rolesPath = "TODO: replace with your roles path";
            this.jwkSetUrl = "TODO: replace with your jwk set url";
        }
    }
}
