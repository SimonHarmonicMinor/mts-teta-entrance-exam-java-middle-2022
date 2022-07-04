package com.example.demo.util;

public interface ValidationUtil {

    static boolean isEmpty(final String value) {
        return value == null || value.isEmpty();
    }

}
