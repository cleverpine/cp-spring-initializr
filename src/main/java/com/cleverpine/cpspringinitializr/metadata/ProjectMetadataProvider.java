package com.cleverpine.cpspringinitializr.metadata;

import com.cleverpine.cpspringinitializr.metadata.strategy.SpringVersionMetadataUpdateStrategy;
import io.spring.initializr.metadata.InitializrMetadata;
import io.spring.initializr.metadata.InitializrMetadataProvider;
import lombok.AllArgsConstructor;
import org.springframework.cache.annotation.Cacheable;

@AllArgsConstructor
public class ProjectMetadataProvider implements InitializrMetadataProvider {

    private InitializrMetadata metadata;
    private final SpringVersionMetadataUpdateStrategy springVersionMetadataUpdateStrategy;

    @Override
    @Cacheable(value = "initializr.metadata", key = "'metadata'")
    public InitializrMetadata get() {
        // TODO: add dependency version update strategy if necessary
        this.metadata = this.springVersionMetadataUpdateStrategy.update(this.metadata);
        return this.metadata;
    }
}
