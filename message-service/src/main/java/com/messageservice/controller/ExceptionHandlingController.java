package com.messageservice.controller;

import com.messageservice.exception.InvalidOperationException;
import com.messageservice.exception.UnauthorizedAccessException;
import com.messageservice.model.ErrorModel;
import com.messageservice.model.ErrorType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import javax.validation.ConstraintViolationException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestControllerAdvice
public class ExceptionHandlingController {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public List<ErrorModel> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        log.error("handleMethodArgumentNotValidException: exception {}", ex.getMessage(), ex);
        return ex.getBindingResult()
                 .getAllErrors()
                 .stream()
                 .map(err -> new ErrorModel(err.getDefaultMessage(), ErrorType.VALIDATION_ERROR_TYPE, LocalDateTime.now()))
                 .collect(Collectors.toList());
    }

    @ExceptionHandler(EntityNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorModel handleEntityNotFoundException(EntityNotFoundException ex) {
        log.error("handleEntityNotFoundException: exception {}", ex.getMessage(), ex);
        return new ErrorModel(ex.getMessage(), ErrorType.NOT_FOUND_ERROR_TYPE, LocalDateTime.now());
    }
    @ExceptionHandler(EntityExistsException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorModel handleEntityExistsException(EntityExistsException ex) {
        log.error("handleEntityExistsException: exception {}", ex.getMessage(), ex);
        return new ErrorModel(ex.getMessage(), ErrorType.NOT_FOUND_ERROR_TYPE, LocalDateTime.now());
    }

    @ExceptionHandler(InvalidOperationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorModel handleInvalidOperationException(InvalidOperationException ex) {
        log.error("handleInvalidOperationException: exception {}", ex.getMessage(), ex);
        return new ErrorModel(ex.getMessage(), ErrorType.INVALID_OPERATION_ERROR_TYPE, LocalDateTime.now());
    }

    @ExceptionHandler(UnauthorizedAccessException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ErrorModel handleUnauthorizedAccessException(UnauthorizedAccessException ex) {
        log.error("handleUnauthorizedAccessException: exception {}", ex.getMessage(), ex);
        return new ErrorModel(ex.getMessage(), ErrorType.UNAUTHORIZED_ACCESS_ERROR_TYPE, LocalDateTime.now());
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorModel handleException(Exception ex) {
        log.error("handleException: exception {}", ex.getMessage(), ex);
        return new ErrorModel(ex.getMessage(), ErrorType.FATAL_ERROR_TYPE, LocalDateTime.now());
    }

    @ExceptionHandler(DuplicateKeyException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorModel handleDuplicateKeyException(DuplicateKeyException ex) {
        log.error("handleDuplicateKeyException: exception {}", ex.getMessage(), ex);
        return new ErrorModel(ex.getMessage(), ErrorType.INVALID_OPERATION_ERROR_TYPE, LocalDateTime.now());
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorModel handleDataIntegrityViolationException(DataIntegrityViolationException ex) {
        log.error("handleDataIntegrityViolationException: exception {}", ex.getMessage(), ex);
        return new ErrorModel(ex.getMessage(), ErrorType.INVALID_DATA_ERROR_TYPE, LocalDateTime.now());
    }


    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorModel constraintViolationException(ConstraintViolationException ex) {
        log.error("ConstraintViolationException: exception {}", ex.getMessage(), ex);
        return new ErrorModel(ex.getMessage(), ErrorType.INVALID_DATA_ERROR_TYPE, LocalDateTime.now());
    }
}
