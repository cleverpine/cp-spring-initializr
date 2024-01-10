package com.cleverpine.cpspringinitializr.generator.contributor.resources;

import com.cleverpine.cpspringinitializr.generator.customizer.yml.YamlPropertiesCustomizer;
import com.cleverpine.cpspringinitializr.model.yml.YamlConfiguration;
import com.cleverpine.cpspringinitializr.writer.YamlFileWriter;
import io.spring.initializr.generator.project.ProjectDescription;
import io.spring.initializr.generator.project.contributor.ProjectContributor;
import io.spring.initializr.generator.spring.util.LambdaSafe;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.core.Ordered;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

import static com.cleverpine.cpspringinitializr.logging.TerminalLogger.logMajorStep;

@RequiredArgsConstructor
public class YamlPropertiesContributor implements ProjectContributor {

    private final String filePath;
    private final YamlFileWriter yamlFileWriter = new YamlFileWriter();
    private final ProjectDescription projectDescription;
    private final ObjectProvider<YamlPropertiesCustomizer> customizers;

    @Override
    public void contribute(Path projectRoot) throws IOException {
        logMajorStep("Generating .yml file...");
        var path = projectRoot.resolve(filePath);
        var yamlConfiguration = new YamlConfiguration();
        this.customizeYaml(yamlConfiguration);
        this.yamlFileWriter.write(yamlConfiguration, path.toString());
    }

    @Override
    public int getOrder() {
        return Ordered.LOWEST_PRECEDENCE;
    }

    private void customizeYaml(YamlConfiguration configuration) {
        List<YamlPropertiesCustomizer> configurationTypeCustomizers = this.customizers.orderedStream().toList();
        LambdaSafe.callbacks(YamlPropertiesCustomizer.class, configurationTypeCustomizers, configuration)
                .invoke((configurationTypeCustomizer) ->
                        configurationTypeCustomizer.customize(configuration, this.projectDescription.getName()));
    }
}
