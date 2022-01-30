package com.example.demo.request;

import java.io.IOException;

/**
 * Create by fkshistero on 30.01.2022.
 */
public class ParseInvalidException extends IOException {
    public ParseInvalidException(String message) {
        super(message);
    }
}
