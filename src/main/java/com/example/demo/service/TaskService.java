package com.example.demo.service;

import com.example.demo.model.Command;
import com.example.demo.model.Task;
import com.example.demo.repository.TaskRepo;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;


/**
 * Сервис управления задачами
 */
public class TaskService {

    /**
     * Ответы
     */
    private static final String CREATED = "CREATED";
    private static final String DELETED = "DELETED";
    private static final String CLOSED = "CLOSED";
    private static final String REOPENED = "REOPENED";
    private static final String WRONG_FORMAT = "WRONG_FORMAT";
    private static final String ACCESS_DENIED = "ACCESS_DENIED";
    private static final String ERROR = "ERROR";

    TaskRepo taskRepo = new TaskRepo();

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
            case  Command.CREATE_TASK:
                response = createTask(cmd.getUser(), cmd.getArg());
                break;
            case  Command.LIST_TASK:
                response = getTaskList(cmd.getArg());
                break;
            case Command.CLOSE_TASK:
            case Command.DELETE_TASK:
            case Command.REOPEN_TASK:
                response = changeTaskStatus(cmd.getUser(), cmd.getArg(), cmd.getCmdName());
                break;
            case Command.__DELETE_ALL:
                taskRepo.deleteAllTasks();
                response = "ALL_TASKS_DELETED";
                break;
            default:
                response = ERROR;
                break;
        }

        return response;
    }

    /**
     * Добавить новую задачу
     * @param user - пользователь от которого выполняется операция
     * @param taskName - имя задачи
     * @return - возвращает статус выполнения операции
     */
    private String createTask(String user, String taskName) {

        try {
            taskRepo.addTask(taskName, user);
        } catch (Exception e) {
            return ERROR;
        }

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

        String response = getResponseByCommandName(cmdName);
        String status = getStatusByCommandName(cmdName);
        Task task = taskRepo.findTaskByName(taskName);

        if (task != null && !checkPermission(task, user)) {
            return ACCESS_DENIED;
        }

        if (task == null || status.equals(ERROR) || !checkTaskFlow(task, status)) {
            return ERROR;
        }

        try {
            taskRepo.changeTaskStatus(taskName, status);
        } catch (Exception e) {
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
                + taskRepo.findTaskByUser(user)
                .stream()
                .sorted(Comparator.comparing(Task::getId))
                .map(Task::getName)
                .collect(Collectors.toList());
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
        if (commandAndArgs.size() == 1 && commandAndArgs.get(0).equals(Command.__DELETE_ALL)) {
            return new Command("", Command.__DELETE_ALL, "", true);
        }

        //Проверяем остальные команды
        if (commandAndArgs.size() == 3) {
            switch (commandAndArgs.get(1)) {
                case Command.CREATE_TASK:
                case Command.CLOSE_TASK:
                case Command.DELETE_TASK:
                case Command.REOPEN_TASK:
                case Command.LIST_TASK:
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
     * Возвращает статус в который перевести задачу в зависимости от команды
     * @param cmdName - имя команды
     * @return возвращает статусы CREATED CLOSED DELETED ERROR
     */
    private String getStatusByCommandName(String cmdName) {

        String status;

        switch (cmdName) {
            case Command.CLOSE_TASK:
                status = CLOSED;
                break;
            case Command.REOPEN_TASK:
                status = CREATED;
                break;
            case Command.DELETE_TASK:
                status = DELETED;
                break;
            default:
                status = ERROR;
        }
        return status;
    }

    /**
     * Возвращает код ответа в зависимости от команды
     * @param cmdName - имя команды
     * @return возвращает коды ответов CLOSED DELETED REOPENED ERROR
     */
    private String getResponseByCommandName(String cmdName) {

        String response;

        switch (cmdName) {
            case Command.DELETE_TASK:
                response = DELETED;
                break;
            case Command.REOPEN_TASK:
                response = REOPENED;
                break;
            case Command.CLOSE_TASK:
                response = CLOSED;
                break;
            default:
                response = ERROR;
        }
        return response;
    }
}
