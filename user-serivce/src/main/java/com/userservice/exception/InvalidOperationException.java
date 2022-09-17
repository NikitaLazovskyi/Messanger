package com.userservice.exception;

public class InvalidOperationException extends ServiceException {
    public InvalidOperationException(String message) {
        super(message);
    }
}
