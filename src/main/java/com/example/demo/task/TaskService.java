package com.example.demo.task;

import com.example.demo.exception.DuplicateTaskException;
import com.example.demo.parser.RequestData;
import com.example.demo.server.ResultType;

import java.nio.file.AccessDeniedException;
import java.util.NoSuchElementException;

public class TaskService {
	private final TaskRepository repo;

	public TaskService(TaskRepository repo) {
		this.repo = repo;
	}

	public String handleRequest(RequestData request) {
		try {
			final TaskParams params = TaskParams.fromRequest(request);
			switch (request.getCommand()) {
				case CREATE_TASK:
					return createTask(params);
				case DELETE_TASK:
					return deleteTask(params);
				case CLOSE_TASK:
					return closeTask(params);
				case REOPEN_TASK:
					return reopenTask(params);
				case LIST_TASK:
					return listTask(request);
				default:
					return ResultType.ERROR.name();
			}
		} catch (DuplicateTaskException | NoSuchElementException | IllegalArgumentException e) {
			return ResultType.ERROR.name();
		} catch (AccessDeniedException e) {
			return ResultType.ACCESS_DENIED.name();
		} catch (Exception e) {
			return ResultType.ERROR.name();
		}
	}

	private String createTask(TaskParams params) throws DuplicateTaskException {
		repo.createTask(params.getUser(), params.getTaskName());
		return ResultType.CREATED.name();
	}

	// package-private for tests only
	String deleteTask(TaskParams params) throws AccessDeniedException, NoSuchElementException {
		repo.deleteTask(params.getUser(), params.getTaskName());
		return ResultType.DELETED.name();
	}

	private String closeTask(TaskParams params) throws AccessDeniedException, NoSuchElementException {
		repo.closeTask(params.getUser(), params.getTaskName());
		return ResultType.CLOSED.name();
	}

	private String reopenTask(TaskParams params) throws AccessDeniedException, NoSuchElementException {
		repo.reopenTask(params.getUser(), params.getTaskName());
		return ResultType.REOPENED.name();
	}

	private String listTask(RequestData request) throws IllegalArgumentException {
		final String tasksOwner = request.getArg().orElseThrow(IllegalArgumentException::new);
		final String tasks = String.join(", ", repo.getUserTasks(tasksOwner));
		return String.format("%s [%s]", ResultType.TASKS.name(), tasks);
	}
}
