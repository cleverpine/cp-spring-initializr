package com.cleverpine.mando.config.jpaspecification;

import com.cleverpine.specification.parser.SingleFilterParser;
import com.cleverpine.specification.parser.SingleSortParser;
import com.cleverpine.specification.parser.SpecificationParserManager;
import com.cleverpine.specification.parser.json.FilterJsonArrayParser;
import com.cleverpine.specification.parser.json.SortJsonArrayParser;
import com.cleverpine.specification.util.ValueConverter;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JpaSpecificationResolverConfig {

    @Bean
    public ValueConverter valueConverter() {
        return new ValueConverter();
    }

    @Bean
    public SingleFilterParser singleFilterParser(ObjectMapper objectMapper) {
        return new FilterJsonArrayParser(objectMapper);
    }

    @Bean
    public SingleSortParser singleSortParser(ObjectMapper objectMapper) {
        return new SortJsonArrayParser(objectMapper);
    }

    @Bean
    public SpecificationParserManager specificationParserManager(SingleFilterParser singleFilterParser, SingleSortParser singleSortParser) {
        return SpecificationParserManager.builder()
                .withSingleFilterParser(singleFilterParser)
                .withSingleSortParser(singleSortParser)
                .build();
    }
}
