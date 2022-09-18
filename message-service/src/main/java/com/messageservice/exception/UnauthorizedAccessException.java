package com.messageservice.exception;

public class UnauthorizedAccessException extends ServiceException {
    public UnauthorizedAccessException(String message) {
        super(message);
    }
}
