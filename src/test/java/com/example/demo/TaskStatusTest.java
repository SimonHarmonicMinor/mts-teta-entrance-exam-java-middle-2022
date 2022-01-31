package com.example.demo;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static com.example.demo.services.task.components.TaskStatus.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TaskStatusTest {

    @Test
    @DisplayName("Проверка справочника статусов и их жизненных циклов")
    void checkTaskStatus() {
        assertEquals(CREATED.getTaskStatusTitle(), "CREATED");
        assertEquals(REOPENED.getTaskStatusTitle(), "REOPENED");
        assertEquals(CLOSED.getTaskStatusTitle(), "CLOSED");
        assertEquals(DELETED.getTaskStatusTitle(), "DELETED");

        assertEquals(1, CREATED.getLifeCycle().getAvailableStatusList().size());
        assertEquals("CLOSED", CREATED.getLifeCycle().getAvailableStatusList().get(0).getTaskStatusTitle());
        assertEquals(0, DELETED.getLifeCycle().getAvailableStatusList().size());
        assertEquals(CREATED.getLifeCycle(), REOPENED.getLifeCycle());
        assertEquals(2, CLOSED.getLifeCycle().getAvailableStatusList().size());
        assertTrue(CLOSED.getLifeCycle().getAvailableStatusList().containsAll(List.of(REOPENED, DELETED)));
    }
}
