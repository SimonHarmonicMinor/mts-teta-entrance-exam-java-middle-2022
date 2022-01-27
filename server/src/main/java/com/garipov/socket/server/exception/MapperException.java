package com.garipov.socket.server.exception;

public class MapperException extends Exception{
    public MapperException(String message, Throwable cause) {
        super(message, cause);
    }

    public MapperException(String message) {
        super(message);
    }

    public MapperException() {
        super();
    }
}
