package com.example.demo;

import com.example.demo.entity.Request;
import com.example.demo.entity.Response;
import com.example.demo.entity.Task;
import com.example.demo.entity.enums.Result;
import com.example.demo.entity.enums.TaskState;
import com.example.demo.services.TaskService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * Класс выполнения запросов
 */
public class TaskRepository {

    private final TaskService taskService;
    private static final Logger LOG = LoggerFactory.getLogger(TaskRepository.class);

    public TaskRepository(TaskService service){
        this.taskService = service;
    }

    /**
     * Список задач
     * @param request запрос
     * @return ответ
     */
    public Response getList(Request request){
        LOG.debug("Got request to list of task");
        List<Task> tasks = this.taskService.getList(request.getArg());
        return new Response.Builder().result(Result.TASKS).arg(tasks).build();
    }

    /**
     * Создание задачи
     * @param request запрос
     * @return ответ
     */
    public Response createTask(Request request){
        LOG.debug("Got request to create task");
        Response.Builder builder = new Response.Builder();
        Task task = null;
        try{
            task = this.taskService.getTask(request.getUser(), request.getArg());
        } catch (Exception ignored){}

        try{
            if(task == null){
                this.taskService.create(request.getUser(), request.getArg());
                builder.result(Result.CREATED);
            } else{
                LOG.error("Task with name {} already exist", task.getName());
                throw new Exception("Already has");
            }
        } catch (Exception exp){
            LOG.error("Exception at create task: {}", exp.getMessage());
            builder.result(Result.ERROR);
        }

        return builder.build();
    }

    /**
     * Удаление задачи
     * @param request запрос
     * @return ответ
     */
    public Response deleteTask(Request request){
        LOG.debug("Got request to delete task");
        Response.Builder builder = new Response.Builder();
        try{
            Task task = this.taskService.getTask(request.getUser(), request.getArg());
            if(task != null) {
                this.taskService.delete(task);
                builder.result(Result.DELETED);
                LOG.debug("Task {} deleted by user {}", task.getName(), request.getUser());
            } else{
                builder.result(Result.ACCESS_DENIED);
                LOG.error("User {} does't have access to task {}", request.getUser(),  request.getArg());
            }
        } catch(Exception exp){
            LOG.error("Exception at delete task: {}", exp.getMessage());
            builder.result(Result.ERROR);
        }
        return builder.build();
    }

    /**
     * Переоткрытие задачи
     * @param request запрос
     * @return ответ
     */
    public Response reopenTask(Request request){
        LOG.debug("Got request to reopen task");
        return this.changeState(request, TaskState.CREATED);
    }

    /**
     * Закрытие задачи
     * @param request запрос
     * @return ответ
     */
    public Response closeTask(Request request){
        LOG.debug("Got request to close task");
        return this.changeState(request, TaskState.CLOSED);
    }

    /**
     * Изменение статуса задачи
     * @param request запрос
     * @param state новый статус
     * @return ответ
     */
    private Response changeState(Request request, TaskState state){
        Response.Builder builder = new Response.Builder();
        try{
            Task task = this.taskService.getTask(request.getUser(), request.getArg());
            if(task != null) {
                this.taskService.changeState(task, state);
                builder.result(state == TaskState.CREATED ? Result.REOPENED : Result.CLOSED);
                LOG.debug("Task {} change state to {}", task.getName(),  task.getState());
            } else{
                LOG.error("User {} does't have access to task {}", request.getUser(),  request.getArg());
                builder.result(Result.ACCESS_DENIED);
            }
        } catch(Exception exp){
            LOG.error("Exception at change task state: {}", exp.getMessage());
            builder.result(Result.ERROR);
        }
        return builder.build();
    }
}
