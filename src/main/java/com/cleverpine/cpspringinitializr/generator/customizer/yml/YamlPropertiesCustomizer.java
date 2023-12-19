package com.cleverpine.cpspringinitializr.generator.customizer.yml;

import com.cleverpine.cpspringinitializr.model.yml.YamlConfiguration;
import org.springframework.core.Ordered;

@FunctionalInterface
public interface YamlPropertiesCustomizer extends Ordered {

    void customize(YamlConfiguration configuration, String applicationName);

    @Override
    default int getOrder() {
        return 0;
    }
}
