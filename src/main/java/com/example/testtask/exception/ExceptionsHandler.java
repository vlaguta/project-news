package com.example.testtask.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@Slf4j
@ControllerAdvice
public class ExceptionsHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<Response> handleBusinessException(BusinessException exception) {

        Response response = new Response(exception.getMessage());
        log.error("There was an error" + exception);
        return new ResponseEntity<>(response, getStatus(exception));
    }

    private HttpStatus getStatus(BusinessException exception) {

        return exception.getHttpCode().map(HttpStatus::valueOf)
                .orElse(HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(Throwable.class)
    public ResponseEntity<Response> handleException(Exception exception) {

        Response response = new Response(exception.getMessage());
        log.error("There was an error" + exception);
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
