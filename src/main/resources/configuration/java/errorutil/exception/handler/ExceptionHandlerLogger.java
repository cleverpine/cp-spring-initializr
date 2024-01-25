package com.cleverpine.{applicationName}.exception.handler;

import com.cleverpine.cpspringerrorutil.logger.Logger;
import org.apache.logging.log4j.LogManager;
import org.springframework.stereotype.Component;

@Component
public class ExceptionHandlerLogger implements Logger {

    private final org.apache.logging.log4j.Logger log = LogManager.getLogger(ExceptionHandlerLogger.class);

    @Override
    public void error(String message, Throwable throwable) {
        log.error(message, throwable);
    }
}
