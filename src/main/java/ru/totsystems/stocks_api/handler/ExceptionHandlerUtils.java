package ru.totsystems.stocks_api.handler;

import org.apache.commons.lang3.exception.ExceptionUtils;

public class ExceptionHandlerUtils {
    private ExceptionHandlerUtils() {
    }

    public static String buildErrorMessage(Throwable t) {
        StringBuilder message =
                new StringBuilder(ExceptionUtils.getMessage(t));

        Throwable cause;
        if ((cause = t.getCause()) != null) {
            message.append(", cause: ").append(ExceptionUtils.getMessage(cause));
        }

        return message.toString();
    }
}
