package com.example.demo.server;

import com.example.demo.parser.RequestData;
import com.example.demo.parser.RequestParser;
import com.example.demo.task.TaskCommandType;
import com.example.demo.task.TaskRepository;
import com.example.demo.task.TaskService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ControllerTest {
	Controller controller;
	TaskService serviceMock;

	@BeforeEach
	void beforeEach() {
		serviceMock = mock(TaskService.class);
		controller = new Controller(new RequestParser(), serviceMock);
	}

	@Test
	void processRequest() {
		when(serviceMock.handleRequest(any(RequestData.class))).thenReturn("ACCESS_DENIED");
		assertEquals("ACCESS_DENIED", controller.processRequest("PETYA DELETE_TASK CleanRoom"));
	}

	@Test
	@DisplayName("Should return WRONG_FORMAT on missing argument")
	void processRequest2() {
		assertEquals("WRONG_FORMAT",
				controller.processRequest("VASYA CREATE_TASK   \n\t"));
	}
}