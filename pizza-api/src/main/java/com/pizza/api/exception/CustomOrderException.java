package com.pizza.api.exception;

public class CustomOrderException extends RuntimeException {
    public CustomOrderException(String message) {
        super(message);
    }
}
