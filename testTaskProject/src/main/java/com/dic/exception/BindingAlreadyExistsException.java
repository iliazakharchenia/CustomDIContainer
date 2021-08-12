package com.dic.exception;

public class BindingAlreadyExistsException extends RuntimeException {
    public BindingAlreadyExistsException(String message) {
        super(message);
    }
}
