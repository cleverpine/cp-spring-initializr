package com.cleverpine.cpspringinitializr.logging;

import com.cleverpine.cpspringinitializr.enums.LogTypeEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MarkerFactory;

public class TerminalLogger {

    private static final String separator =
            "----------------------------------------------------------------------------------------------------------------------------";
    private static final Logger logger = LoggerFactory.getLogger("TerminalLogger");

    public static void log(String message, Object... args) {
        logger.info(message, args);
    }

    public static void logError(String message, Object... args) {
        var marker = MarkerFactory.getDetachedMarker(LogTypeEnum.ERROR.name());
        logger.error(marker, message, args);
    }

    public static void logMajorStep(String message) {
        var marker = MarkerFactory.getDetachedMarker(LogTypeEnum.MAJOR_STEP.name());
        logger.info(separator);
        logger.info(marker, message);
        logger.info(separator);
    }
}
