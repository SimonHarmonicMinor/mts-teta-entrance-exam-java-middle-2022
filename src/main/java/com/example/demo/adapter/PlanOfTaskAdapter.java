package com.example.demo.adapter;

import ch.qos.logback.classic.Logger;
import com.example.demo.entity.Command;
import com.example.demo.entity.Request;
import com.example.demo.exception.ExceptionHandler;
import com.example.demo.exception.FormatException;
import com.example.demo.service.PlanOfTask;
import com.example.demo.service.specificCheckers.UserNameChecker;
import org.slf4j.LoggerFactory;

/**
 * Адаптер для преобразовании запроса в виде строки в сущность Request
 */

public class PlanOfTaskAdapter {

    private final PlanOfTask planOfTask;
    private final ExceptionHandler exceptionHandler;

    public PlanOfTaskAdapter(PlanOfTask planOfTask, ExceptionHandler exceptionHandler) {
        this.planOfTask = planOfTask;
        this.exceptionHandler = exceptionHandler;
    }

    private static final Logger logger
            = (Logger) LoggerFactory.getLogger(UserNameChecker.class);

    /**
     * @param line - запрос, пример: "VASYA CREATE_TASK CleanRoom"
     * @return result - результат выполнения запроса, пример: "CREATED"
     */

    public String execute(String line) {
        logger.info(">>PlanOfTaskAdapter execute line={}", line);
        try {
            String[] arrayLine = line.split(" ");
            if (arrayLine.length != 3) {
                throw new FormatException("Неправильный формат команды");
            } else {
                Request request = new Request();
                request.setUserName(arrayLine[0]);
                request.setCommand(Command.valueOf(arrayLine[1]));
                request.setAdditionalParam(arrayLine[2]);
                String result = planOfTask.execute(request);
                logger.info("<<PlanOfTaskAdapter execute result={}", result);
                return result;
            }
        } catch (Exception e) {
//            e.printStackTrace();
            logger.error("<<PlanOfTaskAdapter exception in execute()", e.getCause());
            return exceptionHandler.handle(e);
        }
    }
}
