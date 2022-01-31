package com.example.demo.server;

import com.example.demo.exception.RequestFormatException;
import com.example.demo.parser.RequestData;
import com.example.demo.parser.RequestParser;
import com.example.demo.task.TaskService;

public class Controller {
	private final RequestParser parser;
	private final TaskService taskService;

	public Controller(RequestParser parser, TaskService taskService) {
		this.parser = parser;
		this.taskService = taskService;
	}

	String processRequest(String requestString) {
		try {
			final RequestData request = parser.parse(requestString);
			return taskService.handleRequest(request);
		} catch (RequestFormatException e) {
			return ResultType.WRONG_FORMAT.name();
		}
	}
}
