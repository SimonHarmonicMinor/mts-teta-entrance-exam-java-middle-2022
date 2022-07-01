package com.example.demo.service;

import com.example.demo.model.Command;
import com.example.demo.model.Task;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;


/**
 * Сервис управления задачами
 */
public class TaskService {

    private static final String CREATE_TASK = "CREATE_TASK";
    private static final String CLOSE_TASK = "CLOSE_TASK";
    private static final String DELETE_TASK = "DELETE_TASK";
    private static final String REOPEN_TASK = "REOPEN_TASK";
    private static final String LIST_TASK = "LIST_TASK";
    private static final String __DELETE_ALL = "__DELETE_ALL";

    private static final String CREATED = "CREATED";
    private static final String DELETED = "DELETED";
    private static final String CLOSED = "CLOSED";
    private static final String REOPENED = "REOPENED";
    private static final String WRONG_FORMAT = "WRONG_FORMAT";
    private static final String ACCESS_DENIED = "ACCESS_DENIED";
    private static final String ERROR = "ERROR";

    //Хранилище задач
    private final Map<String, Task> taskMap = new HashMap<>();

    //Счетчик ID-шников для задач.
    private long id = 0;

    /**
     * Обрабатывает поступивший запрос
     * @param request - строка запроса
     * @return возвращает результат выполнения запроса
     */
    public String execCommand(String request) {

        String response;

        Command cmd = validateRequest(request);

        if (!cmd.isValid()) {
            return WRONG_FORMAT;
        }

        switch (cmd.getCmdName()) {
            case  CREATE_TASK:
                response = createTask(cmd.getUser(), cmd.getArg());
                break;
            case  LIST_TASK:
                response = getTaskList(cmd.getArg());
                break;
            case CLOSE_TASK:
            case DELETE_TASK:
            case REOPEN_TASK:
                response = changeTaskStatus(cmd.getUser(), cmd.getArg(), cmd.getCmdName());
                break;
            case __DELETE_ALL:
                taskMap.clear();
                response = "ALL_TASKS_DELETED";
                break;
            default:
                response = ERROR;
                break;
        }

        return response;
    }

    private String createTask(String user, String taskName) {

        if (isTaskExist(taskName)) {
            return ERROR;
        }

        taskMap.put(taskName, new Task(nextId(), taskName, user, CREATED, LocalDateTime.now()));

        return CREATED;
    }

    /**
     * Изменят текущий статус задачи на новый в соответствии с поступившей командой
     * @param user - пользователь от которого выполняется команда
     * @param taskName - имя задачи у которой должен быть изменен статус
     * @param cmdName - имя команды (CLOSE_TASK DELETE_TASK REOPEN_TASK)
     * @return возвращает результат выполнения команды
     */
    private String changeTaskStatus(String user, String taskName, String cmdName) {

        String response;
        String status = getStatusByCommandName(cmdName);
        Task task = getTaskByName(taskName);

        if (task != null && !checkPermission(task, user)) {
            return ACCESS_DENIED;
        }

        if (task == null || status.equals(ERROR) || !checkTaskFlow(task, status)) {
            return ERROR;
        }

        taskMap.get(taskName).setStatus(status);

        switch (cmdName) {
            case DELETE_TASK:
                response = DELETED;
                break;
            case REOPEN_TASK:
                response = REOPENED;
                break;
            case CLOSE_TASK:
                response = CLOSED;
                break;
            default:
                response = ERROR;
        }

        return response;
    }

    /**
     * Возвращает список задач пользователя за исключением задач в статусе DELETED
     * @param user - пользователь
     * @return возвращает список задач пользователя в формате "TASKS [MyTask1, MyTask2]"
     */
    private String getTaskList(String user) {

        return "TASKS "
                + taskMap
                .values()
                .stream()
                .filter(t -> t.getUser().equals(user) && !t.getStatus().equals(DELETED))
                .sorted(Comparator.comparing(Task::getId))
                .map(Task::getName)
                .collect(Collectors.toList());
    }

    /**
     * Проверяет, существует ли задача с таким именем не в статусе "DELETED"
     * @param taskName - имя задачи
     * @return - true если задача существует
     *           false если задача не существует
     */
    private boolean isTaskExist(String taskName) {

        Task task = getTaskByName(taskName);

        return !(task == null || task.getStatus().equals(DELETED));
    }

    /**
     * Проверяет, соответствует ли строка запроса требуемому формату
     * @param request - строка запроса
     * @return возвращает разобранную строку запроса и результат проверки на валидность.
     */
    private Command validateRequest(String request) {

        List<String> commandAndArgs = Stream.of(request.trim().split(" "))
                .collect(Collectors.toList());

        //Сперва проверяем команду __DELETE_ALL
        if (commandAndArgs.size() == 1 && commandAndArgs.get(0).equals(__DELETE_ALL)) {
            return new Command("", __DELETE_ALL, "", true);
        }

        //Проверяем остальные команды
        if (commandAndArgs.size() == 3) {
            switch (commandAndArgs.get(1)) {
                case CREATE_TASK:
                case CLOSE_TASK:
                case DELETE_TASK:
                case REOPEN_TASK:
                case LIST_TASK:
                    return new Command(
                            commandAndArgs.get(0),
                            commandAndArgs.get(1),
                            commandAndArgs.get(2),
                            true);
                default:
                    return new Command("", "", "", false);
            }
        } else {
            return new Command("", "", "", false);
        }
    }

    /**
     * Получить задачу по ее названию
     * @param taskName - название задачи
     * @return возвращает задачу, если она найдена или null, если не найдена.
     */
    private Task getTaskByName(String taskName) {

        return taskMap.get(taskName);

    }

    /**
     * Проверяет разрешения пользователя на возможность закрывать, удалять и заново открывать задачи
     * @param task - задача
     * @param user - пользователь от которого выполняется операция
     * @return true - если пользователю доступно выполнение операции
     *         false - если пользователю недоступно выполнение операции
     */
    private boolean checkPermission(Task task, String user) {

        return task.getUser().equals(user);

    }

    /**
     * Проверят на возможность перевода задачи из одного статуса в другой
     * @param task - задача
     * @param newStatus - новый статус задачи
     * @return true - если в новый статус переводить разрешено
     *         false - если в новый статус переводить запрещено
     */
    private boolean checkTaskFlow(Task task, String newStatus) {

        if (task.getStatus().equals(DELETED)) {

            return false;
        }

        if (task.getStatus().equals(CREATED) && (newStatus.equals(DELETED) || newStatus.equals(CREATED))) {
            return false;
        }

        return true;
    }

    /**
     * Выделяет следующий ID для новой задачи
     * @return - возвращает новый ID
     */
    private long nextId() {
        return ++id;
    }

    /**
     * Возвращает статус в который перевести задачу в зависимости от команды
     * @param cmdName - имя команды
     * @return возвращает статусы CREATED CLOSED DELETED ERROR
     */
    private String getStatusByCommandName(String cmdName) {

        String status;

        switch (cmdName) {
            case CLOSE_TASK:
                status = CLOSED;
                break;
            case REOPEN_TASK:
                status = CREATED;
                break;
            case DELETE_TASK:
                status = DELETED;
                break;
            default:
                status = ERROR;
        }
        return status;
    }
}
