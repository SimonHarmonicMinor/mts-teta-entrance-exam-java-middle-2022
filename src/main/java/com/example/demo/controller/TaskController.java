package com.example.demo.controller;

import com.example.demo.domain.Task;
import com.example.demo.helper.Answer;
import com.example.demo.domain.Command;
import com.example.demo.helper.Parser;
import com.example.demo.service.TaskServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.stream.Collectors;

public class TaskController {

    private TaskServiceImpl taskService = new TaskServiceImpl();
    private Parser parser = new Parser();

    private static final Logger logger = LoggerFactory.getLogger(TaskController.class);

    public String sendCommand(String inputString){
        Command command = parser.stringToCommand(inputString);

        if (command.getAction() == null)
            return Answer.WRONG_FORMAT.toString();

        switch(command.getAction()){
            case CREATE_TASK:
                return createTask(command).toString();
            case CLOSE_TASK:
                return closeTask(command).toString();
            case DELETE_TASK:
                return deleteTask(command).toString();
            case REOPEN_TASK:
                return reopenTask(command).toString();
            case LIST_TASK:
                return listTask(command);
        }
        return Answer.WRONG_FORMAT.toString();
    }

    /**
     * Создать задачу
     * @param command входная строка после парса
     * @return один из возможных вариантов ответа (enum Answer)
     */
    private Answer createTask(Command command){
        logger.debug("start createTask");
        return taskService.save(command.getArg(), command.getUserName());
    }

    /**
     * Закрыть задачу
     * @param command входная строка после парса
     * @return один из возможных вариантов ответа (enum Answer)
     */
    private Answer closeTask(Command command){
        logger.debug("start closeTask");
        return taskService.close(command.getArg(), command.getUserName());
    }

    /**
     * УДалить задачу
     * @param command входная строка после парса
     * @return один из возможных вариантов ответа (enum Answer)
     */
    private Answer deleteTask(Command command){
        logger.debug("start deleteTask");
        return taskService.delete(command.getArg(), command.getUserName());
    }

    /**
     * Заново открыть задачу
     * @param command входная строка после парса
     * @return один из возможных вариантов ответа (enum Answer)
     */
    private Answer reopenTask(Command command){
        logger.debug("start reopenTask");
        return taskService.reopen(command.getArg(), command.getUserName());
    }

    /**
     * Получить список задач пользователя
     * @param command входная строка после парса
     * @return список задач
     */
    private String listTask(Command command){
        logger.debug("start listTask");
        return Answer.TASKS + " " + taskService.selectAll(command.getArg()).stream()
                                                                        .map(Task::getName)
                                                                        .collect(Collectors.toList()).toString();
    }
}
