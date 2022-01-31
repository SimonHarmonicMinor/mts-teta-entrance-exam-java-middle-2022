package com.example.demo.exception;

/**
 * Любые ошибки, кроме "неверного формата запроса" и "нет прав на совершение операции"
 */
public class ErrorException extends RuntimeException {

    public ErrorException(String message) {
        super(message);
    }
}
