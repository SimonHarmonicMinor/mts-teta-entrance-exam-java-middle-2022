package com.example.demo.util;

public class RequestParser {
    private static String DELIMITER = " ";

    public static String[] parse(String request) {
        return request.split(DELIMITER);
    }
}
