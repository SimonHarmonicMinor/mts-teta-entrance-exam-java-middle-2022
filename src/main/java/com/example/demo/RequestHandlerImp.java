package com.example.demo;

import com.example.demo.entity.Request;
import com.example.demo.entity.Response;
import com.example.demo.entity.enums.Result;
import com.example.demo.services.TaskService;

/**
 * Класс обработки запросов.
 * Реализует интерфейс {@link RequestHandler}
 */
public class RequestHandlerImp implements RequestHandler {
    private final TaskRepository taskRepository = new TaskRepository(new TaskService(new TaskStore()));

    /**
     * Обработка запросов в виде строки
     * @param request строка с запросов
     * @return строка ответа
     */
    public String handle(String request){
        try{
            return handle(parseRequest(request)).toString();
        } catch (IllegalArgumentException exception){
            return new Response.Builder().result(Result.WRONG_FORMAT).build().toString();
        }
    }

    /**
     * Обработка запросов {@link Request}
     * @param request объект запроса
     * @return объект ответа {@link Response}
     */
    public Response handle(Request request){
        switch(request.getCommand()){
            case LIST_TASK:
                return taskRepository.getList(request);
            case CREATE_TASK:
                return taskRepository.createTask(request);
            case DELETE_TASK:
                return taskRepository.deleteTask(request);
            case REOPEN_TASK:
                return taskRepository.reopenTask(request);
            case CLOSE_TASK:
                return taskRepository.closeTask(request);
        }
        return new Response.Builder().result(Result.WRONG_FORMAT).build();
    }

    /**
     * Разбор запроса из строки
     * @param str запрос в виде строки
     * @return объект запроса
     * @throws IllegalArgumentException ошибка при разборе запроса
     */
    private Request parseRequest(String str) throws IllegalArgumentException {
        String[] data = str.split(" ");
        if(data.length < 3) throw new IllegalArgumentException();
        return new Request.Builder()
                .user(data[0])
                .command(data[1])
                .arg(data[2])
                .build();
    }

    @Override
    public String handle(Object o) {
        if(o.getClass() == String.class){
            return handle((String) o);
        } else {
            return handle((Request) o).toString();
        }
    }
}
