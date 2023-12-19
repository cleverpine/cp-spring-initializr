package com.cleverpine.cpspringinitializr.factory;

import com.cleverpine.cpspringinitializr.model.yml.YamlConfiguration;
import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.TypeDescription;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.nodes.Tag;
import org.yaml.snakeyaml.representer.Representer;

public class YamlFactory {

    public Yaml createYaml() {
        var dumperOptions = this.createDumperOptions();
        var representer = this.createRepresenter(dumperOptions);
        return new Yaml(representer, dumperOptions);
    }

    private DumperOptions createDumperOptions() {
        var dumperOptions = new DumperOptions();
        dumperOptions.setDefaultFlowStyle(DumperOptions.FlowStyle.BLOCK);
        dumperOptions.setPrettyFlow(true);
        dumperOptions.setIndent(2);
        return dumperOptions;
    }

    private Representer createRepresenter(DumperOptions dumperOptions) {
        var representer = new Representer(dumperOptions);
        var type = new TypeDescription(YamlConfiguration.class, Tag.MAP);
        representer.addTypeDescription(type);
        return representer;
    }
}
