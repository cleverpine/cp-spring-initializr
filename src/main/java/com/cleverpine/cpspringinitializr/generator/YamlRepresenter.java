package com.cleverpine.cpspringinitializr.generator;

import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.introspector.Property;
import org.yaml.snakeyaml.nodes.NodeTuple;
import org.yaml.snakeyaml.nodes.Tag;
import org.yaml.snakeyaml.representer.Representer;

public class YamlRepresenter extends Representer {

    public YamlRepresenter(DumperOptions options) {
        super(options);
    }

    @Override
    protected NodeTuple representJavaBeanProperty(Object javaBean, Property property, Object propertyValue, Tag customTag) {
        if (propertyValue == null) {
            return null;
        }
        return super.representJavaBeanProperty(javaBean, property, propertyValue, customTag);
    }
}
