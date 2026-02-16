package com.pizza.api.exception;

public class OrderNotFoundException extends RuntimeException {
    public OrderNotFoundException(String message) {
        super(message);
    }

    public OrderNotFoundException(int orderId) {
        super("Order not found with ID: " + orderId);
    }
}
