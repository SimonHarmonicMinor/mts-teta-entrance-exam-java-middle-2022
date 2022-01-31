package com.example.demo.service.mapper;

import com.example.demo.service.dto.CommandParams;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class CommandMapperImplTest {

    private CommandMapper commandMapper = new CommandMapperImpl();

    @Test
    @DisplayName("Проверка маппинга в случае команды получения списка задачи")
    void listTaskToCommandParamsTest() {
        var expected = new CommandParams("VASYA", "LIST_TASK", null, "PETYA");
        CommandParams actual = commandMapper.toCommandParams("VASYA LIST_TASK PETYA");
        Assertions.assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Проверка маппинга в случае всех команд без аргумента, кроме получения списка задач")
    void notListTaskWithoutArgumentToCommandParamsTest() {
        var expected = new CommandParams("VASYA", "CREATE_TASK", "MyTask");
        CommandParams actual = commandMapper.toCommandParams("VASYA CREATE_TASK MyTask");
        Assertions.assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Проверка маппинга в случае всех команд с аргументом, кроме получения списка задач")
    void notListTaskWithArgumentToCommandParamsTest() {
        var expected = new CommandParams("VASYA", "CREATE_TASK", "MyTask", "ARG");
        CommandParams actual = commandMapper.toCommandParams("VASYA CREATE_TASK MyTask ARG");
        Assertions.assertEquals(expected, actual);
    }
}