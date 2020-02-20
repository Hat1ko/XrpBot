package com.hatiko.ripple.wrapper.exception;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.hatiko.ripple.wrapper.dto.ErrorInfoDto;
import com.hatiko.ripple.wrapper.exception.integration.RemoteMsPassThroughException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.util.Collections;

@Slf4j
@AllArgsConstructor
@ControllerAdvice
public class GlobalControllerExceptionHandler {

    private static final String MESSAGE_PREFIX = " | Message: ";

    @ExceptionHandler(BaseException.class)
    public ResponseEntity<ErrorInfoDto> handleBaseException(BaseException e, HttpServletRequest request) {
        log(request, e);

        ErrorInfoDto errorInfoDto = createErrorDto(e);
        return new ResponseEntity<>(errorInfoDto, e.getExceptionCode().getHttpStatus());
    }

    @ExceptionHandler(ServletException.class)
    public ResponseEntity<ErrorInfoDto> handleServletException(ServletException e, HttpServletRequest request) {
        logError(request, e);
        ErrorInfoDto errorInfoDto = createErrorDto(ExceptionCode.SOME_PARAMETERS_ABSENT_OR_INVALID_EXCEPTION,
                ExceptionCode.SOME_PARAMETERS_ABSENT_OR_INVALID_EXCEPTION.getDebugMessage());
        return ResponseEntity.badRequest().body(errorInfoDto);
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ErrorInfoDto> handleRuntimeException(RuntimeException e, HttpServletRequest request) {
        logError(request, e);

        ErrorInfoDto errorInfoDto = createErrorDto(ExceptionCode.UNKNOWN_EXCEPTION, ExceptionCode.UNKNOWN_EXCEPTION.getDebugMessage());
        return new ResponseEntity<>(errorInfoDto, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(RemoteMsPassThroughException.class)
    public ResponseEntity<ErrorInfoDto> handleRemoteMsPassThroughException(RemoteMsPassThroughException e, HttpServletRequest request) {
        ErrorInfoDto errorInfoDto = e.getErrorInfoDto();
        logWarning(request, e.getMessage());
        return new ResponseEntity<>(errorInfoDto, HttpStatus.valueOf(errorInfoDto.getStatus()));
    }

    private void log(HttpServletRequest request, BaseException baseException) {
        String message = request.getRequestURI() + "?" + request.getQueryString() + MESSAGE_PREFIX + baseException.getMessage();
        if (baseException.getExceptionCode().getHttpStatus() == HttpStatus.INTERNAL_SERVER_ERROR) {
            log.error(message);
        } else {
            log.warn(message);
        }
    }

    private static ErrorInfoDto createErrorDto(BaseException baseException) {
        ExceptionCode exceptionCode = baseException.getExceptionCode();
        ErrorInfoDto errorInfoDto = new ErrorInfoDto();
        errorInfoDto.setCode(exceptionCode.getCode());
        errorInfoDto.setStatus(exceptionCode.getHttpStatus().value());
        errorInfoDto.setMessages(Collections.singletonList(exceptionCode.getDebugMessage()));

        return errorInfoDto;
    }

    private static ErrorInfoDto createErrorDto(ExceptionCode exceptionCode, String message) {
        ErrorInfoDto errorInfoDto = new ErrorInfoDto();
        errorInfoDto.setCode(exceptionCode.getCode());
        errorInfoDto.setStatus(exceptionCode.getHttpStatus().value());
        errorInfoDto.setMessages(Collections.singletonList(message));

        return errorInfoDto;
    }

    private void logError(HttpServletRequest request, Exception exception) {
        String message = request.getRequestURI() + "?" + request.getQueryString();
        log.error(message, exception);
    }

    private void logWarning(HttpServletRequest request, String errorMessage) {
        String message = request.getRequestURI() + "?" + request.getQueryString() + MESSAGE_PREFIX + errorMessage;
        log.warn(message);
    }
}
