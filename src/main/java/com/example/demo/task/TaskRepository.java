package com.example.demo.task;

import com.example.demo.exception.DuplicateTaskException;

import java.nio.file.AccessDeniedException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.util.Map.entry;

public class TaskRepository {
	private static final Map<TaskStatus, TaskStatus> ALLOWED_PREV_STATUSES = Map.ofEntries(
			entry(TaskStatus.CLOSED, TaskStatus.CREATED),
			entry(TaskStatus.CREATED, TaskStatus.CLOSED),
			entry(TaskStatus.DELETED, TaskStatus.CLOSED)
	);

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
			TaskStatus newStatus) throws AccessDeniedException, NoSuchElementException {
		final var allowedStatus = ALLOWED_PREV_STATUSES.get(newStatus);
		final var task = Optional
				.ofNullable(allTasks.get(taskName))
				.orElseThrow(() -> new NoSuchElementException(String.format(
						"Task '%s' not found", taskName
				)));
		if (!task.getUser().equals(actingUser)) {
			throw new AccessDeniedException(String.format(
					"Task '%s' created by another user", taskName
			));
		}
		if (task.getStatus() != allowedStatus) {
			throw new AccessDeniedException(String.format(
					"Task '%s' in status '%s' can't become '%s'",
					taskName,
					task.getStatus(),
					newStatus
			));
		}
		return task;
	}

	public void deleteTask(String actingUser,
	                       String taskName) throws AccessDeniedException, NoSuchElementException {
		final var task = findTaskAndCheckAccess(actingUser, taskName, TaskStatus.DELETED);
		task.setStatus(TaskStatus.DELETED);
	}

	public void closeTask(String actingUser,
	                      String taskName) throws AccessDeniedException, NoSuchElementException {
		final var task = findTaskAndCheckAccess(actingUser, taskName, TaskStatus.CLOSED);
		task.setStatus(TaskStatus.CLOSED);
	}

	public void reopenTask(String actingUser,
	                       String taskName) throws AccessDeniedException, NoSuchElementException {
		final var task = findTaskAndCheckAccess(actingUser, taskName, TaskStatus.CREATED);
		task.setStatus(TaskStatus.CREATED);
	}

	public List<String> getUserTasks(String user) {
		return allTasks
				.values()
				.stream()
				.filter(task -> task.getStatus() != TaskStatus.DELETED && task.getUser().equals(user))
				.map(Task::getName)
				.collect(Collectors.toList());
	}

	// For tests only
	public void reset() {
		allTasks.clear();
	}
}
