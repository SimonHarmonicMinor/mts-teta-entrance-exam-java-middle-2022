package com.example.demo.service.specificCheckers;

import ch.qos.logback.classic.Logger;
import com.example.demo.entity.Command;
import com.example.demo.entity.Request;
import com.example.demo.exception.FormatException;
import com.example.demo.service.RequestChecker;
import org.slf4j.LoggerFactory;

import java.util.EnumSet;

/**
 * Проверка запроса на правильность команды
 */

public class CommandNameChecker implements RequestChecker {

    private static final Logger logger
            = (Logger) LoggerFactory.getLogger(UserNameChecker.class);

    @Override
    public void check(Request request) {
        logger.info(">>CommandNameChecker check request={}", request);
        if (!EnumSet.allOf(Command.class).contains(request.getCommand())) {
            throw new FormatException("Некорректная команда");
        }
    }
}
