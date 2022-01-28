package com.example.demo.type;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface MyLittleBean {
    /**
     * Это маркерная аннотация, нужна только для более наглядного демонстрирования,
     * что данный класс где-то инжектится через ServiceLoader
     * Другой смысловой нагрузки не несет
     */
}
