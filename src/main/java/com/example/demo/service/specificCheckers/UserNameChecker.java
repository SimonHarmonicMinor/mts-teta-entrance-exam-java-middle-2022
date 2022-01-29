package com.example.demo.service.specificCheckers;

import ch.qos.logback.classic.Logger;
import com.example.demo.entity.Request;
import com.example.demo.exception.FormatException;
import com.example.demo.service.RequestChecker;
import org.slf4j.LoggerFactory;


/**
 * Проверка имени юзера на написание в upper case
 */
public class UserNameChecker implements RequestChecker {

    private static final Logger logger
            = (Logger) LoggerFactory.getLogger(UserNameChecker.class);

    @Override
    public void check(Request request) {
        logger.info(">>UserNameCheker check request={}", request);
        if (!request.getUserName().chars().allMatch(Character::isUpperCase)) {
            throw new FormatException("Неправильный формат написания имени");
        }
    }

}