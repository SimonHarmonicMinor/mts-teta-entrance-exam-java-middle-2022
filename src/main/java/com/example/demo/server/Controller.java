package com.example.demo.server;

import com.example.demo.parser.RequestData;
import com.example.demo.exception.RequestFormatException;
import com.example.demo.parser.RequestParser;
import com.example.demo.task.TaskCommandType;
import com.example.demo.task.TaskService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Controller {
	private static final Logger LOG = LoggerFactory.getLogger(Controller.class);

	private final RequestParser parser;
	private final TaskService taskService;

	public Controller(RequestParser parser, TaskService taskService) {
		this.parser = parser;
		this.taskService = taskService;
	}

	String processRequest(String requestString) {
		try {
			final RequestData request = parser.parse(requestString);
			if (request.getCommand() instanceof TaskCommandType) {
				return taskService.handleRequest(request);
			}
			LOG.error("Unknown command type {}", request.getCommand().getClass().getSimpleName());
			return ResultType.ERROR.name();
		} catch (RequestFormatException e) {
			return ResultType.WRONG_FORMAT.name();
		}
	}
}
