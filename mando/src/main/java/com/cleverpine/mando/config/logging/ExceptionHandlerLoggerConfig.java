package com.cleverpine.mando.config.logging;

import com.cleverpine.cpspringerrorutil.mapper.BaseExceptionTypeMapper;
import com.cleverpine.cpspringerrorutil.mapper.ExceptionTypeMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ExceptionHandlerLoggerConfig {

    @Bean
    public ExceptionTypeMapper exceptionTypeMapper() {
        return new BaseExceptionTypeMapper();
    }
}