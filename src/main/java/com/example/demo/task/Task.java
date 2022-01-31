package com.example.demo.task;

final class Task {
	private final String user;
	private final String name;
	private TaskStatus status = TaskStatus.CREATED;

	public Task(String user, String name) {
		this.user = user;
		this.name = name;
	}

	public String getUser() {
		return user;
	}
	public String getName() {
		return name;
	}

	public TaskStatus getStatus() {
		return this.status;
	}

	void setStatus(TaskStatus status) {
		this.status = status;
	}
}