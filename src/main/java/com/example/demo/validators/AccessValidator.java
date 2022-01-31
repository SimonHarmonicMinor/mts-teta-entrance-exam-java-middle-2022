package com.example.demo.validators;

import com.example.demo.entity.Task;
import com.example.demo.entity.User;
import com.example.demo.exceptions.AccessDeniedException;

/**
 * Валидация, имеет ли пользователь права на совершение операции
 */
public class AccessValidator {

    private AccessValidator() {}

    public static void validate(User user, Task task) {
        if (!user.getTasks().contains(task)) {
            throw new AccessDeniedException();
        }
    }
}
