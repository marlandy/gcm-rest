package com.autentia.tutorial.gcm.api.exception;

//@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Invalid input data")
public class InvalidInputDataException extends RuntimeException {

    public InvalidInputDataException() {}

    public InvalidInputDataException(String message) {
        super(message);
    }

}
