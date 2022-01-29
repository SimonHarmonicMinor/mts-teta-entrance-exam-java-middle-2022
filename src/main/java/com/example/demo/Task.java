package com.example.demo;

import java.util.Map;

public class Task {
	private State state;
	final private String task_name;
	final private User user;
	
	private Task(User user, String task_name) {
		state = State.CREATED;
		this.task_name = task_name;
		this.user = user;
	}

	private static Task getMyTask(Map<String, Task> tasks, String user_name, String task_name) throws ErrAccess, ErrOther {
		Task task = tasks.get(task_name);
		if (task == null)
			throw new ErrOther();
		if (!(task.user.getName().equals(user_name)))
			throw new ErrAccess();
		return task;
	}
	
	public static void create(Map<String, Task> tasks, User user, String task_name) throws ErrOther {
		if (tasks.containsKey(task_name))
			throw new ErrOther();
		Task task = new Task(user, task_name);
		tasks.put(task_name, task);
		user.addTask(task_name);
	}
	
	public static void delete(Map<String, Task> tasks, String user_name, String task_name) throws ErrAccess, ErrOther {
		Task task = getMyTask(tasks, user_name, task_name);
		if (task.state != State.CLOSED)
			throw new ErrOther();
		tasks.remove(task.task_name);
		task.user.delTask(task_name);
	}

	public static void close(Map<String, Task> tasks, String user_name, String task_name) throws ErrAccess, ErrOther {
		Task task = getMyTask(tasks, user_name, task_name);
		if (task.state != State.CREATED)
			throw new ErrOther();
		task.state = State.CLOSED;
	}

	public static void reopen(Map<String, Task> tasks, String user_name, String task_name) throws ErrAccess, ErrOther {
		Task task = getMyTask(tasks, user_name, task_name);
		if (task.state != State.CLOSED)
			throw new ErrOther();
		task.state = State.CREATED;
	}
}
