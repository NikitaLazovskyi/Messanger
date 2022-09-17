package com.userservice.exception;

public class UnauthorizedAccessException extends ServiceException {
    public UnauthorizedAccessException(String message) {
        super(message);
    }
}
