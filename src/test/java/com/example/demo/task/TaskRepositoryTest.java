package com.example.demo.task;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.nio.file.AccessDeniedException;
import java.util.List;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class TaskRepositoryTest {
	TaskRepository repo;

	@BeforeEach
	void beforeEach() {
		repo = new TaskRepository();
	}

	@Test
	@DisplayName("createTask: should create")
	void createTask_shouldThrowOnNoneExisting() {
		repo.createTask("VASYA", "CleanRoom");
		assertEquals(List.of("CleanRoom"), repo.getUserTasks("VASYA"));
	}

	@Test
	@DisplayName("deleteTask: should throw on none-existing task")
	void deleteTask_shouldThrowOnNoneExisting() {
		assertThrows(NoSuchElementException.class,
				() -> repo.deleteTask("PETYA", "CleanRoom")
		);
	}

	@Test
	@DisplayName("deleteTask: should throw on someone else's task")
	void deleteTask_shouldThrowOnSomeoneElsesTask() {
		repo.createTask("VASYA", "CleanRoom");
		assertThrows(AccessDeniedException.class,
				() -> repo.deleteTask("PETYA", "CleanRoom")
		);
	}
}