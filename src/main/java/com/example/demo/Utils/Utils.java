package com.example.demo.Utils;

import java.util.ServiceLoader;

public class Utils {

    public static <T>  T getBean(ServiceLoader<T> serviceLoader) {
        return serviceLoader.iterator().next();
    }

}
