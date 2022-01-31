package com.example.demo.service.validation;

/**
 * Сервис валидации
 */
public interface ValidationService {

    /**
     * Валидация запроса
     *
     * @param command команда пользователя
     * @return результат валидации, успешно - true, ошибка - false
     */
    boolean validateRequest(String command);
}