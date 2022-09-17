package com.userservice.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class ErrorModel {

    private String message;
    private ErrorType errorType;
    private LocalDateTime timeStamp;

}
