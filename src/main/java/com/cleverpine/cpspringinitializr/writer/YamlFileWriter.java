package com.cleverpine.cpspringinitializr.writer;

import com.cleverpine.cpspringinitializr.factory.YamlFactory;
import com.cleverpine.cpspringinitializr.model.yml.YamlConfiguration;

import java.io.FileWriter;
import java.io.IOException;

public class YamlFileWriter {

    private final YamlFactory yamlFactory = new YamlFactory();

    public void write(YamlConfiguration yamlConfiguration, String path) throws IOException {
        var writer = new FileWriter(path);
        var yaml = yamlFactory.createYaml();
        yaml.dump(yamlConfiguration, writer);
    }
}
