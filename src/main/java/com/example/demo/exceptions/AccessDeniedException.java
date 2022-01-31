package com.example.demo.exceptions;

public class AccessDeniedException extends RuntimeException {

    public AccessDeniedException() {
        super("Нет прав на совершение операции");
    }
}
