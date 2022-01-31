package com.example.demo.exception;

import ch.qos.logback.classic.Logger;
import com.example.demo.entity.Result;
import com.example.demo.service.specificCheckers.UserNameChecker;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * Реализация ExceptionHandler
 */
public class ExceptionHandlerImpl implements ExceptionHandler {

    private static final Logger logger
            = (Logger) LoggerFactory.getLogger(UserNameChecker.class);

    private final Map<String, Result> resultMap = new HashMap<>();

    {
        resultMap.put(FormatException.class.getCanonicalName(), Result.WRONG_FORMAT);
        resultMap.put(RightException.class.getCanonicalName(), Result.ACCESS_DENIED);
        resultMap.put(ErrorException.class.getCanonicalName(), Result.ERROR);
    }

    /**
     * @param e - исключение
     * @return результат обработки исключения
     */

    @Override
    public String handle(Exception e) {
        logger.info(">>ExceptionHandlerImpl handle", e);
        String result = resultMap.getOrDefault(e.getClass().getCanonicalName(), Result.ERROR).name();
        logger.info("<<ExceptionHandlerImpl handle result={}", result);
        return result;
    }
}
