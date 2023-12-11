package com.cleverpine.cpspringinitializr.config;

import com.cleverpine.cpspringinitializr.metadata.ProjectMetadataProvider;
import com.cleverpine.cpspringinitializr.metadata.strategy.SpringVersionMetadataUpdateStrategy;
import io.spring.initializr.generator.project.ProjectGenerationConfiguration;
import io.spring.initializr.metadata.InitializrMetadata;
import io.spring.initializr.metadata.InitializrMetadataBuilder;
import io.spring.initializr.metadata.InitializrMetadataProvider;
import io.spring.initializr.metadata.InitializrProperties;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@ProjectGenerationConfiguration
@EnableConfigurationProperties(InitializrProperties.class)
public class InitializrMetadataConfiguration {

    @Bean
    @ConditionalOnMissingBean(InitializrMetadataProvider.class)
    public InitializrMetadataProvider initializrMetadataProvider(InitializrProperties properties, SpringVersionMetadataUpdateStrategy strategy) {
        InitializrMetadata metadata = InitializrMetadataBuilder.fromInitializrProperties(properties).build();
        return new ProjectMetadataProvider(metadata, strategy);
    }

    @Bean
    @ConditionalOnMissingBean(InitializrMetadata.class)
    public InitializrMetadata initializrMetadata(InitializrMetadataProvider metadataProvider) {
        // TODO: 'initializr-web' module registers this bean at Runtime in the 'ProjectGenerationInvoker' class - is it better to do it there?
        return metadataProvider.get();
    }

    @Bean
    @ConditionalOnMissingBean(SpringVersionMetadataUpdateStrategy.class)
    public SpringVersionMetadataUpdateStrategy springVersionUpdateStrategy() {
        return new SpringVersionMetadataUpdateStrategy(new RestTemplate());
    }
}
