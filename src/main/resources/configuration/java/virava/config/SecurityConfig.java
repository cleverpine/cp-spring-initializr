package com.cleverpine.{applicationName}.config;

import com.cleverpine.viravaspringhelper.filter.ViravaFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final ViravaFilter authoritiesFilter;

    @Autowired
    public SecurityConfig(ViravaFilter authoritiesFilter) {
        this.authoritiesFilter = authoritiesFilter;
    }

    @Bean
    protected SecurityFilterChain configure(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests((requests ->
                requests.requestMatchers("/swagger-ui/**", "/v3/api-docs/**", "/api/system/**")
                        .permitAll()
                        .anyRequest()
                        .authenticated()));

        http
                .csrf()
                .disable()
                .addFilterAfter(authoritiesFilter, BasicAuthenticationFilter.class)
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        return http.build();
    }
}
