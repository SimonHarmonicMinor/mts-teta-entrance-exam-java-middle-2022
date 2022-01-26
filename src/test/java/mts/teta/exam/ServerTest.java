package mts.teta.exam;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.*;

import java.util.Arrays;
import java.util.stream.Collectors;

class ServerTest extends AbstractServerTest {

  private static String tasksListResponse(String... taskNames)
  {
    return ResultType.TASKS.name()+" ["+ String.join(",", taskNames) +"]";
  }

  /**
   * Создание таска для user1
   */
  @Test
   void CreateTaskUser1() {
    String response = sendMessage(String.format("%s %s %s","user1", Command.CommandType.CREATE_TASK.name(),"task1"));
    assertEquals(ResultType.CREATED.name(), response);
    response = sendMessage(String.format("%s %s %s","user1", Command.CommandType.LIST_TASK.name(),"user1"));
    assertEquals(tasksListResponse("task1"), response);
  }

    /**
     * Попытка создания дубликата таска под тем же пользователем
     */
    @Test
    void CreateDuplicateTaskUser1() {
        CreateTaskUser1();
        String response = sendMessage(String.format("%s %s %s","user1", Command.CommandType.CREATE_TASK.name(),"task1"));
        assertEquals(ResultType.ERROR.name(), response);
    }

    /**
     * Попытка создания дубликата таска под другим пользователем
     */
    @Test
    void CreateDuplicateTaskUser2() {
        CreateTaskUser1();
        String response = sendMessage(String.format("%s %s %s","user2", Command.CommandType.CREATE_TASK.name(),"task1"));
        assertEquals(ResultType.ERROR.name(), response);
    }

    /**
     * Создание второго таска для user1
     */
    @Test
    void CreateAnotherTaskUser1() {
        CreateTaskUser1();
        String response = sendMessage(String.format("%s %s %s","user1", Command.CommandType.CREATE_TASK.name(),"task2"));
        assertEquals(ResultType.CREATED.name(), response);
        response = sendMessage(String.format("%s %s %s","user1", Command.CommandType.LIST_TASK.name(),"user1"));
        assertEquals(tasksListResponse("task1","task2"), response);
    }

    /**
     * Создание таска для user2
     */
    @Test
    void CreateTaskUser2() {
        CreateTaskUser1();
        String response = sendMessage(String.format("%s %s %s","user2", Command.CommandType.CREATE_TASK.name(),"task1_user2"));
        assertEquals(ResultType.CREATED.name(), response);
        response = sendMessage(String.format("%s %s %s","user2", Command.CommandType.LIST_TASK.name(),"user2"));
        assertEquals(tasksListResponse("task1_user2"), response);
    }

    /**
     * Получение текущего списка тасков для user1 от user2
     */
    @Test
    void GetTasksUser1ByUser2() {
        CreateTaskUser2();
        String response = sendMessage(String.format("%s %s %s","user2", Command.CommandType.LIST_TASK.name(),"user1"));
        assertEquals(tasksListResponse("task1"), response);
    }

    /**
     * Закрытие таска
     */
    @Test
    void CloseTasksUser1() {
        CreateAnotherTaskUser1();
        String response = sendMessage(String.format("%s %s %s","user1", Command.CommandType.CLOSE_TASK.name(),"task1"));
        assertEquals(ResultType.CLOSED.name(), response);
        response = sendMessage(String.format("%s %s %s","user1", Command.CommandType.LIST_TASK.name(),"user1"));
        assertEquals(tasksListResponse("task1","task2"), response);
    }

    /**
     * Закрытие закрытого таска
     */
    @Test
    void CloseClosedTasksUser1() {
        CloseTasksUser1();
        String response = sendMessage(String.format("%s %s %s","user1", Command.CommandType.CLOSE_TASK.name(),"task1"));
        assertEquals(ResultType.ERROR.name(), response);
    }

    /**
     * Открытие закрытого таска
     */
    @Test
    void ReopenClosedTasksUser1() {
        CloseTasksUser1();
        String response = sendMessage(String.format("%s %s %s","user1", Command.CommandType.REOPEN_TASK.name(),"task1"));
        assertEquals(ResultType.REOPENED.name(), response);
        response = sendMessage(String.format("%s %s %s","user1", Command.CommandType.LIST_TASK.name(),"user1"));
        assertEquals(tasksListResponse("task1","task2"), response);
    }

    /**
     * Удаление открытого таска
     */
    @Test
    void DeleteOpenTasksUser1() {
        CreateAnotherTaskUser1();
        String response = sendMessage(String.format("%s %s %s","user1", Command.CommandType.DELETE_TASK.name(),"task1"));
        assertEquals(ResultType.ERROR.name(), response);
        response = sendMessage(String.format("%s %s %s","user1", Command.CommandType.LIST_TASK.name(),"user1"));
        assertEquals(tasksListResponse("task1","task2"), response);
    }

    /**
     * Удаление закрытого таска
     */
    @Test
    void DeleteClosedTasksUser1() {
        CloseTasksUser1();
        String response = sendMessage(String.format("%s %s %s","user1", Command.CommandType.DELETE_TASK.name(),"task1"));
        assertEquals(ResultType.DELETED.name(), response);
        response = sendMessage(String.format("%s %s %s","user1", Command.CommandType.LIST_TASK.name(),"user1"));
        assertEquals(tasksListResponse("task2"), response);
    }

    /**
     * Создание таска после его удаления для user1
     */
    @Test
    void CreateTaskUser1_1() {
        DeleteClosedTasksUser1();
        String response = sendMessage(String.format("%s %s %s","user1", Command.CommandType.CREATE_TASK.name(),"task1"));
        assertEquals(ResultType.CREATED.name(), response);
        response = sendMessage(String.format("%s %s %s","user1", Command.CommandType.LIST_TASK.name(),"user1"));
        assertEquals(tasksListResponse("task2","task1"), response);
    }
}