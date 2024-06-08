package com.example.demo.exceptions;

public class DatabaseOperationException extends RuntimeException {
    public DatabaseOperationException(String message, Throwable cause) {
        super(message, cause);
    }
}