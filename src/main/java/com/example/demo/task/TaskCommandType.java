package com.example.demo.task;

import com.example.demo.exception.UnknowmCommandException;

import java.util.Arrays;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.util.function.Function.identity;

public enum TaskCommandType {
	CREATE_TASK,
	DELETE_TASK,
	CLOSE_TASK,
	REOPEN_TASK,
	LIST_TASK;

	private static final Map<String, TaskCommandType> COMMAND_TYPE_MAP =
			Arrays.stream(TaskCommandType.values())
					.collect(Collectors.toMap(Enum::name, identity()));

	public static TaskCommandType parseCommand(String command) throws UnknowmCommandException {
		return Optional.ofNullable(COMMAND_TYPE_MAP.get(command))
				.orElseThrow(() -> new UnknowmCommandException(
						String.format("Unknown command: '%s'", command)
				));
	}

	public boolean shouldHaveArgument() {
		return true;
	}
}
