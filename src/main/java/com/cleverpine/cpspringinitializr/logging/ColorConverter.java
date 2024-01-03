package com.cleverpine.cpspringinitializr.logging;

import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.pattern.CompositeConverter;
import com.cleverpine.cpspringinitializr.enums.LogTypeEnum;
import org.springframework.boot.ansi.AnsiColor;
import org.springframework.boot.ansi.AnsiOutput;

public class ColorConverter extends CompositeConverter<ILoggingEvent> {

    @Override
    protected String transform(ILoggingEvent event, String in) {
        var logColor = this.resolverColor(event);
        return AnsiOutput.toString(logColor, in);
    }

    private AnsiColor resolverColor(ILoggingEvent event) {
        var markers = event.getMarkerList();

        if (markers == null || markers.size() == 0) {
            return AnsiColor.DEFAULT;
        }

        var logType = markers.stream()
                .map(marker -> LogTypeEnum.valueOf(marker.getName()))
                .findFirst()
                .orElse(LogTypeEnum.DEFAULT);

        return switch (logType) {
            case MAJOR_STEP -> AnsiColor.MAGENTA;
            case ERROR -> AnsiColor.RED;
            default -> AnsiColor.DEFAULT;
        };
    }
}
