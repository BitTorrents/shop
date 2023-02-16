package com.shop.exception;

public class OutOfStockException extends Throwable {

    public OutOfStockException(String message) {
        super(message);
    }
}
