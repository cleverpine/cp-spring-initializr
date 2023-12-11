package com.cleverpine.cpspringinitializr.metadata.strategy;

import io.spring.initializr.generator.version.Version;
import io.spring.initializr.generator.version.VersionParser;
import io.spring.initializr.metadata.DefaultMetadataElement;
import io.spring.initializr.metadata.InitializrMetadata;
import lombok.RequiredArgsConstructor;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@RequiredArgsConstructor
public class SpringVersionMetadataUpdateStrategy {

    private static final String VERSION_RELEASE_SUFFIX = "RELEASE";
    private static final String VERSION_SNAPSHOT_SUFFIX = "SNAPSHOT";
    private final RestTemplate restTemplate;

    public InitializrMetadata update(InitializrMetadata currentMetadata) {
        var latestSpringBootMetadata = this.fetchLatestSpringBootMetadata();

        if (latestSpringBootMetadata == null) {
            return currentMetadata;
        }

        var defaultSpringBootMetadata = currentMetadata.getBootVersions().getDefault();
        var defaultSpringBootVersionId = defaultSpringBootMetadata.getId();

        var latestSpringBootVersionId = latestSpringBootMetadata.getVersion();

        if (defaultSpringBootVersionId.equals(latestSpringBootVersionId)) {
            return currentMetadata;
        }

        var version = VersionParser.DEFAULT.safeParse(latestSpringBootVersionId);

        if (version == null) {
            return currentMetadata;
        }

        var updatedSpringBootMetadata = this.createDefaultSpringBootMetadata(latestSpringBootVersionId, version);
        currentMetadata.updateSpringBootVersions(List.of(updatedSpringBootMetadata));

        return currentMetadata;
    }

    private SpringVersionMetadata fetchLatestSpringBootMetadata() {
        // TODO: make this configurable
        // TODO: add logs? what would they do?
        var url = "https://api.spring.io/projects/spring-boot/releases/current";
        SpringVersionMetadata latestSpringBootMetadata;
        try {
            latestSpringBootMetadata = restTemplate.getForObject(url, SpringVersionMetadata.class);
        } catch (RestClientException e) {
            return null;
        }
        return latestSpringBootMetadata;
    }

    private DefaultMetadataElement createDefaultSpringBootMetadata(String versionId, Version version) {
        var defaultSpringBootMetadata = new DefaultMetadataElement();
        defaultSpringBootMetadata.setId(versionId);
        defaultSpringBootMetadata.setName(this.determineDisplayName(version));
        defaultSpringBootMetadata.setDefault(true);
        return defaultSpringBootMetadata;
    }

    private String determineDisplayName(Version version) {
        StringBuilder sb = new StringBuilder();
        sb.append(version.getMajor()).append(".").append(version.getMinor()).append(".").append(version.getPatch());
        if (version.getQualifier() != null) {
            sb.append(determineSuffix(version.getQualifier()));
        }
        return sb.toString();
    }

    private String determineSuffix(Version.Qualifier qualifier) {
        String id = qualifier.getId();
        if (id.equals(VERSION_RELEASE_SUFFIX)) {
            return "";
        }
        StringBuilder sb = new StringBuilder(" (");
        if (id.contains(VERSION_SNAPSHOT_SUFFIX)) {
            sb.append(VERSION_SNAPSHOT_SUFFIX);
        } else {
            sb.append(id);
            if (qualifier.getVersion() != null) {
                sb.append(qualifier.getVersion());
            }
        }
        sb.append(")");
        return sb.toString();
    }
}
