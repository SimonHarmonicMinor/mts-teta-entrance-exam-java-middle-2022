package com.example.demo.service.specificCheckers;

import ch.qos.logback.classic.Logger;
import com.example.demo.entity.Request;
import com.example.demo.exception.FormatException;
import com.example.demo.service.RequestChecker;
import org.slf4j.LoggerFactory;

import static java.util.Objects.isNull;

/**
 * Проверка запроса на заполнение обязательных полей запроса
 */

public class RequiredFieldsChecker implements RequestChecker {

    private static final Logger logger
            = (Logger) LoggerFactory.getLogger(UserNameChecker.class);

    @Override
    public void check(Request request) {
        logger.info(">>RequiredFieldsChecker check request={}", request);
        if (isNull(request) || isNull(request.getCommand()) || isNull(request.getUserName()) || isNull(request.getAdditionalParam())) {
            throw new FormatException("Необходимый параметр null");
        }
    }
}
