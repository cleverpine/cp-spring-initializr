package com.cleverpine.mando.config.virava;

import com.cleverpine.mando.auth.roles.Resources;
import com.cleverpine.mando.auth.roles.Roles;
import com.cleverpine.viravaspringhelper.config.AuthTokenConfig;
import com.cleverpine.viravaspringhelper.config.RoleConfig;
import com.cleverpine.viravaspringhelper.core.ViravaJwtVerifier;
import com.cleverpine.viravaspringhelper.filter.ViravaFilter;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "auth.token")
public class ViravaHelperConfig {

    private String usernamePath;
    private String rolesPath;
    private String jwkSetUrl;

    @Bean
    public RoleConfig<Roles, Resources> roleConfig() {
        return new RoleConfig<>(Roles.values(), Resources.values());
    }

    @Bean
    public AuthTokenConfig decodingAuthTokenConfig() {
        return new AuthTokenConfig.Builder().withUsernamePath(usernamePath).withRolesPath(rolesPath).withJwkSetUrl(jwkSetUrl).build();
    }

    @Bean
    public ViravaFilter viravaFilter(RoleConfig<Roles, Resources> roleConfig, ObjectMapper objectMapper, AuthTokenConfig authTokenConfig) {
        return new ViravaFilter(roleConfig, objectMapper, authTokenConfig, new ViravaJwtVerifier(authTokenConfig));
    }

    public void setUsernamePath(String usernamePath) {
        this.usernamePath = usernamePath;
    }

    public void setRolesPath(String rolesPath) {
        this.rolesPath = rolesPath;
    }

    public void setJwkSetUrl(String jwkSetUrl) {
        this.jwkSetUrl = jwkSetUrl;
    }
}