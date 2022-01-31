package com.example.demo.task;

import com.example.demo.parser.RequestData;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.nio.file.AccessDeniedException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;

class TaskServiceTest {
	TaskRepository repoMock;
	TaskService service;

	@BeforeEach
	void beforeEach() {
		repoMock = mock(TaskRepository.class);
		service = new TaskService(repoMock);
	}

	@Test
	@DisplayName("deleteTask: throws when repository throws")
	void deleteTask() throws AccessDeniedException {
		doThrow(new AccessDeniedException("Access denied"))
				.when(repoMock).deleteTask(anyString(), anyString());
		assertThrows(AccessDeniedException.class,
				() -> service.deleteTask(new TaskParams("user", "task"))
		);
	}

	@Test
	@DisplayName("deleteTask: throws when repository throws")
	void handleRequest() throws AccessDeniedException {
		doThrow(new AccessDeniedException("Access denied"))
				.when(repoMock).deleteTask(anyString(), anyString());
		final var request = new RequestData("user",
				TaskCommandType.DELETE_TASK,
				Optional.of("task"));
		final var result = service.handleRequest(request);
		assertEquals(
				"ACCESS_DENIED",
				result
		);
	}
}