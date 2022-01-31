package com.example.demo.exception;

/**
 * Ошибки "нет прав на совершение операции"
 */
public class RightException extends RuntimeException {

    public RightException(String message) {
        super(message);
    }
}
