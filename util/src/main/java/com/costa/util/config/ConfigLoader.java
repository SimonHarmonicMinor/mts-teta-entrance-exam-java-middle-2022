package com.costa.util.config;

import com.costa.util.config.exception.ConfigLoadException;

import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Properties;

public class ConfigLoader {

    private ConfigLoader() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }

    public static void load(Class<?> configClass, String fileName) {
        try {
            Properties props = new Properties();
            try (InputStream propStream = ConfigLoader.class.getClassLoader().getResourceAsStream(fileName)) {
                props.load(propStream);
            }
            for (Field field : configClass.getDeclaredFields())
                if (Modifier.isStatic(field.getModifiers())){
                    field.setAccessible(true);
                    field.set(null, getValue(props, field.getName(), field.getType()));
                }
        } catch (Exception e) {
            throw new ConfigLoadException("Error with loading configuration", e);
        }
    }

    private static Object getValue(Properties props, String name, Class<?> type) {
        String value = props.getProperty(name);
        if (value == null)
            throw new IllegalArgumentException("Missing configuration value: " + name);
        if (type == String.class)
            return value;
        if (type == boolean.class)
            return Boolean.parseBoolean(value);
        if (type == int.class)
            return Integer.parseInt(value);
        if (type == float.class)
            return Float.parseFloat(value);
        throw new IllegalArgumentException("Unknown configuration value type: " + type.getName());
    }
}
