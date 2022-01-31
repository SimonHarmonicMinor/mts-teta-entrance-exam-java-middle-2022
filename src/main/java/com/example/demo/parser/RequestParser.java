package com.example.demo.parser;

import com.example.demo.exception.RequestFormatException;
import com.example.demo.task.TaskCommandType;

import java.util.Optional;

public class RequestParser {
	public RequestData parse(String request) throws RequestFormatException {
		final var parts = request.trim().split("\\s+", 3);
		if (parts.length < 2) {
			throw new RequestFormatException(
					String.format("Wrong request, at ltest two tokens expected: '%s'", request)
			);
		}
		final var user = parts[0];
		final var command = TaskCommandType.parseCommand(parts[1]);
		final var arg = Optional.ofNullable(parts.length > 2 ? parts[2] : null);
		if (command.shouldHaveArgument() && arg.isEmpty()) {
			throw new RequestFormatException(String.format(
					"Command '%s' shoud be supplied an argument", command.name()
			));
		}
		return new RequestData(user, command, arg);
	}
}
