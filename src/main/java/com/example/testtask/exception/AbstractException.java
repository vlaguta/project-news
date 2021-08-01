package com.example.testtask.exception;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Optional;

import static java.util.Optional.ofNullable;

@Data
@AllArgsConstructor
public abstract class AbstractException extends RuntimeException {

    private Integer httpCode;

    public AbstractException(String message) {
        super(message);
    }

    public Optional<Integer> getHttpCode() {
        return ofNullable(httpCode);
    }

}
