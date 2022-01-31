package com.example.demo.exception;

/**
 * Ошибки "неверного формата запроса"
 */
public class FormatException extends RuntimeException {

    public FormatException(String message) {
        super(message);
    }
}
