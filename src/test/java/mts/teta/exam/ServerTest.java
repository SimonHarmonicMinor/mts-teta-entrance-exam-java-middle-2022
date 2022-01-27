package mts.teta.exam;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.*;

class ServerTest extends AbstractServerTest {

    private static String tasksListResponse(String... taskNames) {
        return ResultType.TASKS.name() + " [" + String.join(",", taskNames) + "]";
    }

    private void checkUserTasks(String user, String... taskNames) {
        var response = sendMessage(String.format("%s %s %s", user, Command.CommandType.LIST_TASK.name(), user));
        assertEquals(tasksListResponse(taskNames), response);
    }

    /**
     * Создание таска для user1
     */
    @Test
    void createTaskUser1() {
        String response = sendMessage(String.format("%s %s %s", "user1", Command.CommandType.CREATE_TASK.name(), "task1"));
        assertEquals(ResultType.CREATED.name(), response);
        checkUserTasks("user1", "task1");
    }

    /**
     * Попытка создания дубликата таска под тем же пользователем
     */
    @Test
    void createDuplicateTaskUser1() {
        createTaskUser1();
        String response = sendMessage(String.format("%s %s %s", "user1", Command.CommandType.CREATE_TASK.name(), "task1"));
        assertEquals(ResultType.ERROR.name(), response);
    }

    /**
     * Попытка создания дубликата таска под другим пользователем
     */
    @Test
    void createDuplicateTaskUser2() {
        createTaskUser1();
        String response = sendMessage(String.format("%s %s %s", "user2", Command.CommandType.CREATE_TASK.name(), "task1"));
        assertEquals(ResultType.ERROR.name(), response);
    }

    /**
     * Создание второго таска для user1
     */
    @Test
    void createAnotherTaskUser1() {
        createTaskUser1();
        String response = sendMessage(String.format("%s %s %s", "user1", Command.CommandType.CREATE_TASK.name(), "task2"));
        assertEquals(ResultType.CREATED.name(), response);
        checkUserTasks("user1", "task1", "task2");
    }

    /**
     * Создание таска для user2
     */
    @Test
    void createTaskUser2() {
        createTaskUser1();
        String response = sendMessage(String.format("%s %s %s", "user2", Command.CommandType.CREATE_TASK.name(), "task1_user2"));
        assertEquals(ResultType.CREATED.name(), response);
        checkUserTasks("user2", "task1_user2");
    }

    /**
     * Получение текущего списка тасков для user1 от user2
     */
    @Test
    void getTasksUser1ByUser2() {
        createTaskUser2();
        String response = sendMessage(String.format("%s %s %s", "user2", Command.CommandType.LIST_TASK.name(), "user1"));
        assertEquals(tasksListResponse("task1"), response);
    }

    /**
     * Закрытие таска
     */
    @Test
    void closeTasksUser1() {
        createAnotherTaskUser1();
        String response = sendMessage(String.format("%s %s %s", "user1", Command.CommandType.CLOSE_TASK.name(), "task1"));
        assertEquals(ResultType.CLOSED.name(), response);
        checkUserTasks("user1", "task1", "task2");
    }

    /**
     * Закрытие закрытого таска
     */
    @Test
    void closeClosedTasksUser1() {
        closeTasksUser1();
        String response = sendMessage(String.format("%s %s %s", "user1", Command.CommandType.CLOSE_TASK.name(), "task1"));
        assertEquals(ResultType.ERROR.name(), response);
    }

    /**
     * Переоткрытие закрытого таска
     */
    @Test
    void reopenClosedTasksUser1() {
        closeTasksUser1();
        String response = sendMessage(String.format("%s %s %s", "user1", Command.CommandType.REOPEN_TASK.name(), "task1"));
        assertEquals(ResultType.REOPENED.name(), response);
        checkUserTasks("user1", "task1", "task2");
    }

    /**
     * Удаление открытого таска
     */
    @Test
    void deleteOpenTasksUser1() {
        createAnotherTaskUser1();
        String response = sendMessage(String.format("%s %s %s", "user1", Command.CommandType.DELETE_TASK.name(), "task1"));
        assertEquals(ResultType.ERROR.name(), response);
        checkUserTasks("user1", "task1", "task2");
    }

    /**
     * Удаление закрытого таска
     */
    @Test
    void deleteClosedTasksUser1() {
        closeTasksUser1();
        String response = sendMessage(String.format("%s %s %s", "user1", Command.CommandType.DELETE_TASK.name(), "task1"));
        assertEquals(ResultType.DELETED.name(), response);
        checkUserTasks("user1", "task2");
    }

    /**
     * Создание таска после его удаления для user1
     */
    @Test
    void createTaskAfterDeleteUser1() {
        deleteClosedTasksUser1();
        String response = sendMessage(String.format("%s %s %s", "user1", Command.CommandType.CREATE_TASK.name(), "task1"));
        assertEquals(ResultType.CREATED.name(), response);
        checkUserTasks("user1", "task2", "task1");
    }

    /**
     * Закрытие таска другим пользователем
     */
    @Test
    void closeTasksDiffUser() {
        createAnotherTaskUser1();
        String response = sendMessage(String.format("%s %s %s", "user2", Command.CommandType.CLOSE_TASK.name(), "task1"));
        assertEquals(ResultType.ACCESS_DENIED.name(), response);
        checkUserTasks("user1", "task1", "task2");
    }

    /**
     * Проверка регистрозависимости команд
     */
    @Test
    void caseSensitiveCommands() {
        String response = sendMessage(String.format("%s %s %s", "user1", Command.CommandType.CREATE_TASK.name().toLowerCase(), "task1"));
        assertEquals(ResultType.WRONG_FORMAT.name(), response);
        response = sendMessage(String.format("%s %s %s", "user1", Command.CommandType.LIST_TASK.name().toLowerCase(), "task1"));
        assertEquals(ResultType.WRONG_FORMAT.name(), response);
        response = sendMessage(String.format("%s %s %s", "user1", Command.CommandType.CLOSE_TASK.name().toLowerCase(), "task1"));
        assertEquals(ResultType.WRONG_FORMAT.name(), response);
        response = sendMessage(String.format("%s %s %s", "user1", Command.CommandType.DELETE_TASK.name().toLowerCase(), "task1"));
        assertEquals(ResultType.WRONG_FORMAT.name(), response);
        response = sendMessage(String.format("%s %s %s", "user1", Command.CommandType.REOPEN_TASK.name().toLowerCase(), "task1"));
        assertEquals(ResultType.WRONG_FORMAT.name(), response);
    }

    /**
     * Проверка регистрозависимости имени пользователя
     */
    @Test
    void caseSensitiveUsers() {
        String response = sendMessage(String.format("%s %s %s", "user1", Command.CommandType.CREATE_TASK.name(), "task1"));
        assertEquals(ResultType.CREATED.name(), response);
        checkUserTasks("user1", "task1");
        response = sendMessage(String.format("%s %s %s", "User1", Command.CommandType.CREATE_TASK.name(), "task2"));
        assertEquals(ResultType.CREATED.name(), response);
        checkUserTasks("User1", "task2");
    }

    /**
     * Проверка регистроНЕзависимости названия таска
     */
    @Test
    void caseInsensitiveTasks() {
        String response = sendMessage(String.format("%s %s %s", "user1", Command.CommandType.CREATE_TASK.name(), "task1"));
        assertEquals(ResultType.CREATED.name(), response);
        checkUserTasks("user1", "task1");
        response = sendMessage(String.format("%s %s %s", "user1", Command.CommandType.CREATE_TASK.name(), "Task1"));
        assertEquals(ResultType.ERROR.name(), response);
        response = sendMessage(String.format("%s %s %s", "user1", Command.CommandType.CLOSE_TASK.name(), "tAsk1"));
        assertEquals(ResultType.CLOSED.name(), response);
        response = sendMessage(String.format("%s %s %s", "user1", Command.CommandType.DELETE_TASK.name(), "taSk1"));
        assertEquals(ResultType.DELETED.name(), response);
    }

    /**
     * Проверка неполных команд
     */
    @Test
    void incompleteCommands() {
        String response = sendMessage(String.format("%s %s ", "user1", Command.CommandType.CREATE_TASK.name()));
        assertEquals(ResultType.WRONG_FORMAT.name(), response);
        response = sendMessage(String.format("%s %s", "user1", Command.CommandType.CREATE_TASK.name()));
        assertEquals(ResultType.WRONG_FORMAT.name(), response);
        response = sendMessage(String.format("%s %s", "user1", Command.CommandType.CLOSE_TASK.name()));
        assertEquals(ResultType.WRONG_FORMAT.name(), response);
        response = sendMessage(String.format("%s %s ", "user1", Command.CommandType.DELETE_TASK.name()));
        assertEquals(ResultType.WRONG_FORMAT.name(), response);
    }
}