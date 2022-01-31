package com.example.demo.task;

import com.example.demo.exception.DuplicateTaskException;

import java.nio.file.AccessDeniedException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

public class TaskRepository {
	private final Map<String, Task> allTasks = new LinkedHashMap<>();

	public void createTask(String userName, String taskName) throws DuplicateTaskException {
		if (allTasks.containsKey(taskName)) {
			throw new DuplicateTaskException(String.format(
					"Task '%s' already exists", taskName
			));
		}
		allTasks.put(taskName, new Task(userName, taskName));
	}

	private Task findTaskAndCheckAccess(
			String actingUser,
			String taskName,
			TaskStatus allowedStatus) throws AccessDeniedException, NoSuchElementException {
		final var task = Optional
				.ofNullable(allTasks.get(taskName))
				.orElseThrow();
		if (!task.getUser().equals(actingUser)) {
			throw new AccessDeniedException(String.format(
					"Task '%s' created by another user", taskName
			));
		}
		if (task.getStatus() != allowedStatus) {
			throw new AccessDeniedException(String.format(
					"Task '%s' should be in status '%s'", taskName, allowedStatus
			));
		}
		return task;
	}

	public void deleteTask(String actingUser,
	                       String taskName) throws AccessDeniedException, NoSuchElementException {
		final var task = findTaskAndCheckAccess(actingUser, taskName, TaskStatus.CLOSED);
		task.setStatus(TaskStatus.DELETED);
	}

	public void closeTask(String actingUser,
	                      String taskName) throws AccessDeniedException, NoSuchElementException {
		final var task = findTaskAndCheckAccess(actingUser, taskName, TaskStatus.CREATED);
		task.setStatus(TaskStatus.CLOSED);
	}

	public void reopenTask(String actingUser,
	                       String taskName) throws AccessDeniedException, NoSuchElementException {
		final var task = findTaskAndCheckAccess(actingUser, taskName, TaskStatus.CLOSED);
		task.setStatus(TaskStatus.CLOSED);
	}

	public List<String> getUserTasks(String user) {
		return allTasks
				.values()
				.stream()
				.filter(task -> task.getStatus() != TaskStatus.DELETED && task.getUser().equals(user))
				.map(Task::getName)
				.collect(Collectors.toList());
	}
}
