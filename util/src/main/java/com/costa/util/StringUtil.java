package com.costa.util;

public class StringUtil {
    private StringUtil() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }

    public static boolean isEmpty(Object str){
        return str == null || "".equals(str);
    }
}
