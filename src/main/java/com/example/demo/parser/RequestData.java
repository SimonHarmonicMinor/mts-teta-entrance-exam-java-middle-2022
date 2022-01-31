package com.example.demo.parser;

import com.example.demo.task.TaskCommandType;

import java.util.Optional;

public final class RequestData {
	private final String user;
	private final TaskCommandType command;
	private final Optional<String> arg;

	public RequestData(String user, TaskCommandType command, Optional<String> arg) {
		this.user = user;
		this.command = command;
		this.arg = arg;
	}

	public String getUser() {
		return user;
	}

	public TaskCommandType getCommand() {
		return command;
	}

	public Optional<String> getArg() {
		return arg;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		RequestData that = (RequestData) o;

		if (!user.equals(that.user)) return false;
		if (command != that.command) return false;
		return arg.equals(that.arg);
	}

	@Override
	public int hashCode() {
		int result = user.hashCode();
		result = 31 * result + command.hashCode();
		result = 31 * result + arg.hashCode();
		return result;
	}
}
