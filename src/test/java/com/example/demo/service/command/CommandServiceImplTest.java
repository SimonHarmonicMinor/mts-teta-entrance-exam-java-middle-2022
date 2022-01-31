package com.example.demo.service.command;

import com.example.demo.service.dto.CommandParams;
import com.example.demo.service.mapper.CommandMapper;
import com.example.demo.service.task.TaskService;
import com.example.demo.service.validation.ValidationService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class CommandServiceImplTest {

    private ValidationService validationServiceMock = Mockito.mock(ValidationService.class);
    private CommandMapper commandMapperMock = Mockito.mock(CommandMapper.class);
    private TaskService taskServiceMock = Mockito.mock(TaskService.class);
    private CommandService commandServiceSpy = Mockito.spy(new CommandServiceImpl(commandMapperMock, taskServiceMock, validationServiceMock));

    @Test
    @DisplayName("Проверка работы выполнения команды, если не прошли валидацию")
    void validateRequestTest() {
        Mockito.when(validationServiceMock.validateRequest(Mockito.anyString())).thenReturn(false);
        String actualResponse = commandServiceSpy.execute("test");
        Assertions.assertEquals("WRONG_FORMAT", actualResponse);
    }

    @Test
    @DisplayName("Проверка работы выполнения команды, если получили исключение при маппинге")
    void toCommandParamsTest() {
        Mockito.when(validationServiceMock.validateRequest(Mockito.anyString())).thenReturn(true);
        Mockito.when(commandMapperMock.toCommandParams(Mockito.anyString())).thenThrow(IllegalArgumentException.class);
        String actualResponse = commandServiceSpy.execute("test");
        Assertions.assertEquals("ERROR", actualResponse);
    }

    @Test
    @DisplayName("Проверка работы выполнения команды - создание задачи")
    void createTaskTest() throws Exception {
        Mockito.when(validationServiceMock.validateRequest(Mockito.anyString())).thenReturn(true);
        var commandParams = new CommandParams("VASYA", "CREATE_TASK", "MyTask");
        Mockito.when(commandMapperMock.toCommandParams(Mockito.anyString())).thenReturn(commandParams);
        Mockito.when(taskServiceMock.createTask(commandParams)).thenReturn("CREATED");
        String actualResponse = commandServiceSpy.execute("VASYA CREATE_TASK MyTask");
        Assertions.assertEquals("CREATED", actualResponse);
    }

    @Test
    @DisplayName("Проверка работы выполнения команды - удаление задачи")
    void deleteTaskTest() throws Exception {
        Mockito.when(validationServiceMock.validateRequest(Mockito.anyString())).thenReturn(true);
        var commandParams = new CommandParams("VASYA", "DELETE_TASK", "MyTask");
        Mockito.when(commandMapperMock.toCommandParams(Mockito.anyString())).thenReturn(commandParams);
        Mockito.when(taskServiceMock.deleteTask(commandParams)).thenReturn("DELETED");
        String actualResponse = commandServiceSpy.execute("VASYA DELETE_TASK MyTask");
        Assertions.assertEquals("DELETED", actualResponse);
    }

    @Test
    @DisplayName("Проверка работы выполнения команды - закрытие задачи")
    void closeTaskTest() throws Exception {
        Mockito.when(validationServiceMock.validateRequest(Mockito.anyString())).thenReturn(true);
        var commandParams = new CommandParams("VASYA", "CLOSE_TASK", "MyTask");
        Mockito.when(commandMapperMock.toCommandParams(Mockito.anyString())).thenReturn(commandParams);
        Mockito.when(taskServiceMock.closeTask(commandParams)).thenReturn("CLOSED");
        String actualResponse = commandServiceSpy.execute("VASYA CLOSE_TASK MyTask");
        Assertions.assertEquals("CLOSED", actualResponse);
    }

    @Test
    @DisplayName("Проверка работы выполнения команды - переоткрытие задачи")
    void reOpenTaskTest() throws Exception {
        Mockito.when(validationServiceMock.validateRequest(Mockito.anyString())).thenReturn(true);
        var commandParams = new CommandParams("VASYA", "REOPEN_TASK", "MyTask");
        Mockito.when(commandMapperMock.toCommandParams(Mockito.anyString())).thenReturn(commandParams);
        Mockito.when(taskServiceMock.reOpenTask(commandParams)).thenReturn("REOPENED");
        String actualResponse = commandServiceSpy.execute("VASYA REOPEN_TASK MyTask");
        Assertions.assertEquals("REOPENED", actualResponse);
    }

    @Test
    @DisplayName("Проверка работы выполнения команды - список задач")
    void listTaskTest() throws Exception {
        Mockito.when(validationServiceMock.validateRequest(Mockito.anyString())).thenReturn(true);
        var commandParams = new CommandParams("VASYA", "LIST_TASK", "PETYA");
        Mockito.when(commandMapperMock.toCommandParams(Mockito.anyString())).thenReturn(commandParams);
        Mockito.when(taskServiceMock.listTask(commandParams)).thenReturn("TASKS []");
        String actualResponse = commandServiceSpy.execute("VASYA LIST_TASK PETYA");
        Assertions.assertEquals("TASKS []", actualResponse);
    }

    @Test
    @DisplayName("Проверка работы выполнения команды - в случае получения исключения Exception")
    void checkCatchExceptionTest() throws Exception {
        Mockito.when(validationServiceMock.validateRequest(Mockito.anyString())).thenReturn(true);
        var commandParams = new CommandParams("VASYA", "CREATE_TASK", "MyTask");
        Mockito.when(commandMapperMock.toCommandParams(Mockito.anyString())).thenReturn(commandParams);
        Mockito.when(taskServiceMock.createTask(commandParams)).thenThrow(Exception.class);
        String actualResponse = commandServiceSpy.execute("VASYA CREATE_TASK MyTask");
        Assertions.assertEquals("ERROR", actualResponse);
    }
}