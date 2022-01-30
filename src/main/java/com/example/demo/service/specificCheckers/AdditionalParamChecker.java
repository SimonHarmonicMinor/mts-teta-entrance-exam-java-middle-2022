package com.example.demo.service.specificCheckers;

import ch.qos.logback.classic.Logger;
import com.example.demo.entity.Command;
import com.example.demo.entity.Request;
import com.example.demo.exception.FormatException;
import com.example.demo.service.RequestChecker;
import org.slf4j.LoggerFactory;

/**
 * Проверка правильного написания имени пользователя в дополнительном параметре
 */
public class AdditionalParamChecker implements RequestChecker {

    private static final Logger logger
            = (Logger) LoggerFactory.getLogger(UserNameChecker.class);

    @Override
    public void check(Request request) {
        logger.info(">>AdditionalParamChecker check request={}", request);
        if (request.getCommand().equals(Command.LIST_TASK)) {
            if (!request.getAdditionalParam().chars().allMatch(Character::isUpperCase)) {
                throw new FormatException("Неправильный формат написания имени");
            }
        }
    }
}
