package com.example.demo.controller;

import com.example.demo.format.Answer;
import com.example.demo.format.Command;
import com.example.demo.format.Request;
import com.example.demo.service.TaskService;

import java.util.Objects;

public class TaskController {

    private final TaskService service;

    public TaskController(TaskService service) {
        this.service = service;
    }

    public String receiveRequest(String requestString) {
        Request request = formatValidation(requestString);
        String response = requestString;
        if (Objects.isNull(request)) {
            return Answer.WRONG_FORMAT.toString();
        }
        Answer answer;
        switch (request.getCommand()) {
            case CREATE_TASK:
                answer = service.create(request.getUserName(), request.getArg());
                response = answer.toString();
                break;
            case CLOSE_TASK:
                answer = service.close(request.getUserName(), request.getArg());
                response = answer.toString();
                break;
            case REOPEN_TASK:
                answer = service.reopen(request.getUserName(), request.getArg());
                response = answer.toString();
                break;
            case DELETE_TASK:
                answer = service.delete(request.getUserName(), request.getArg());
                response = answer.toString();
                break;
            case LIST_TASK:
                String taskList = service.listTask(request.getArg());
                response = Answer.TASKS + " " + taskList;
                break;
            case __DELETE_ALL:
                service.deleteAll();
                response = "All data removed";
            default:
                break;
        }
        return response;
    }

    /**
     * Валидация формата.
     * Т.к. в задании не сказано, могут ли имена пользователей и имена задач содержать пробелы или названия команд,
     * то вся строка до первого вхождения команды окружённой пробелами, считается именем пользователя,
     * всё после - аргумент.
     * Иначе хватило бы request.trim().split("\\s+");
     *
     * @param request строка входящего запроса
     * @return запрос разобранный на команду, USER и ARG
     */
    private Request formatValidation(String request) {

        //предположение, что единственный формат использования команды удаления - без имени и аргумента
        if (request.equals(Command.__DELETE_ALL.toString())) {
            return new Request(Command.__DELETE_ALL, null, null);
        }

        String commandStr;
        int index;
        Command command;
        String userName;
        String arg;

        try {
            //CREATE_TASK
            commandStr = " " + Command.CREATE_TASK + " ";
            index = request.indexOf(commandStr);
            if (index >= 0) {
                command = Command.CREATE_TASK;
                userName = request.substring(0, index);
                arg = request.substring(index + commandStr.length());
                return new Request(command, userName, arg);
            }
            //CREATE_TASK
            commandStr = " " + Command.CLOSE_TASK + " ";
            index = request.indexOf(commandStr);
            if (index >= 0) {
                command = Command.CLOSE_TASK;
                userName = request.substring(0, index);
                arg = request.substring(index + commandStr.length());
                return new Request(command, userName, arg);
            }

            //DELETE_TASK
            command = Command.DELETE_TASK;
            commandStr = " " + command + " ";
            index = request.indexOf(commandStr);
            if (index >= 0) {
                userName = request.substring(0, index);
                arg = request.substring(index + commandStr.length());
                return new Request(command, userName, arg);
            }

            //REOPEN_TASK
            command = Command.REOPEN_TASK;
            commandStr = " " + command + " ";
            index = request.indexOf(commandStr);
            if (index >= 0) {
                userName = request.substring(0, index);
                arg = request.substring(index + commandStr.length());
                return new Request(command, userName, arg);
            }

            //LIST_TASK
            command = Command.LIST_TASK;
            commandStr = " " + command + " ";
            index = request.indexOf(commandStr);
            if (index >= 0) {
                userName = request.substring(0, index);
                arg = request.substring(index + commandStr.length());
                return new Request(command, userName, arg);
            }
        } catch (IndexOutOfBoundsException e) {
            return null;
        }
        return null;
    }

}
