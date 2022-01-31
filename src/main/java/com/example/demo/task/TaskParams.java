package com.example.demo.task;

import com.example.demo.parser.RequestData;

class TaskParams {
	private final String user;
	private final String taskName;

	public TaskParams(String user, String taskName) {
		this.user = user;
		this.taskName = taskName;
	}

	public static TaskParams fromRequest(RequestData request) {
		return new TaskParams(request.getUser(),
				request.getArg().orElseThrow(IllegalArgumentException::new));
	}

	public String getUser() {
		return user;
	}

	public String getTaskName() {
		return taskName;
	}
}
