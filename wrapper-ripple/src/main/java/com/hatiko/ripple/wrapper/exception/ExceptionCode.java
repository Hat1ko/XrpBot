package com.hatiko.ripple.wrapper.exception;

import org.springframework.http.HttpStatus;

public enum ExceptionCode {

    NO_SUCH_VALUE_EXCEPTION(40001, "No such value exception", HttpStatus.BAD_REQUEST),
    SOME_PARAMETERS_ABSENT_OR_INVALID_EXCEPTION(40021, "Some parameters are absent, or invalid.", HttpStatus.BAD_REQUEST),
    INTERNAL_SERVICE_EXCEPTION(50101, "Issue with internal service", HttpStatus.INTERNAL_SERVER_ERROR),
    UNKNOWN_EXCEPTION(99999, "Unknown error", HttpStatus.INTERNAL_SERVER_ERROR);

    private final int code;
    private final String debugMessage;
    private final HttpStatus httpStatus;

    ExceptionCode(int code, String debugMessage, HttpStatus httpStatus) {
        this.code = code;
        this.debugMessage = debugMessage;
        this.httpStatus = httpStatus;
    }

    public int getCode() {
        return code;
    }

    public String getDebugMessage() {
        return debugMessage;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }
}

