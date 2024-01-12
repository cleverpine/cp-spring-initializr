package com.cleverpine.cpspringinitializr.generator.contributor.java;

import com.cleverpine.cpspringinitializr.generator.supplier.JavaConfigurationSupplier;
import io.spring.initializr.generator.project.ProjectDescription;
import io.spring.initializr.generator.project.contributor.ProjectContributor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static com.cleverpine.cpspringinitializr.logging.TerminalLogger.log;
import static com.cleverpine.cpspringinitializr.logging.TerminalLogger.logMajorStep;

@RequiredArgsConstructor
public class JavaConfigurationContributor implements ProjectContributor {

    private final PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
    private final String targetResource;
    private final ProjectDescription projectDescription;
    private final ObjectProvider<JavaConfigurationSupplier> suppliers;

    @Override
    public void contribute(Path projectRoot) throws IOException {
        logMajorStep("Generating Java Configurations...");
        var applicationName = projectDescription.getName();
        var targetPath = projectRoot.resolve(targetResource).resolve(applicationName);
        var suppliers = this.suppliers.orderedStream().toList();

        for (JavaConfigurationSupplier supplier : suppliers) {
            var root = this.resolver.getResource(supplier.supply());
            var resources = this.resolver.getResources(supplier.supply() + "/**");
            this.copyResources(resources, root, targetPath, applicationName);
        }
    }

    private void copyResources(Resource[] resources, Resource root, Path targetPath, String applicationName) throws IOException {
        for (Resource resource : resources) {
            if (resource.isReadable()) {
                var filename = extractFileName(root.getURI(), resource.getURI());
                var output = targetPath.resolve(filename);
                Files.createDirectories(output.getParent());
                var content = new String(Files.readAllBytes(Paths.get(resource.getURI())));
                content = content.replace("{applicationName}", applicationName);
                Files.write(output, content.getBytes());
                log("{} file added to project", resource.getFilename());
            }
        }
    }

    private String extractFileName(URI root, URI resource) {
        var candidate = resource.toString().substring(root.toString().length());
        return StringUtils.trimLeadingCharacter(candidate, '/');
    }
}
