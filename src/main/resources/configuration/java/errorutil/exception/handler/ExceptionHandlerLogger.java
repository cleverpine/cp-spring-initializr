package com.cleverpine.{applicationName}.exception.handler;

import com.cleverpine.cpspringerrorutil.logger.Logger;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

@Log4j2
@Component
public class ExceptionHandlerLogger implements Logger {

    @Override
    public void error(String message, Throwable throwable) {
        log.error(message, throwable);
    }
}
