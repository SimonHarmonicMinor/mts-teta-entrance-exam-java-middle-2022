package com.example.demo.exceptions;

public class WrongFormatException extends RuntimeException {

    public WrongFormatException() {
        super("Неверный формат запроса");
    }
}
