package com.booklaunch.booklaunch.exception.handler;

import org.springframework.http.HttpStatus;

import java.time.ZonedDateTime;

/**
 * Le custom excepton vengono inviate al front-end, tramite le classi
 * "ApiException"," ApiExceptionHandler", "ApiRequestException",
 * sia per quanto riguarda lo status HTTP dell’errore generato, sia per quanto riguarda un messaggio che descrive brevemente il problema.
 */
public class ApiException {

    private final String message;
    private final HttpStatus httpStatus;
    private final ZonedDateTime timestamp;


    public ApiException(String message, HttpStatus httpStatus, ZonedDateTime timestamp) {
        this.message = message;
        this.httpStatus = httpStatus;
        this.timestamp = timestamp;
    }

    public String getMessage() {
        return message;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public ZonedDateTime getTimestamp() {
        return timestamp;
    }
}
