package com.example.demo.parser;

import com.example.demo.task.TaskCommandType;

import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

class RequestParserTest {
	final RequestParser parser = new RequestParser();

	@Test
	void parse() {
		final var request = parser.parse("PETYA DELETE_TASK CleanRoom");
		final var expected = new RequestData("PETYA",
				TaskCommandType.DELETE_TASK,
				Optional.of("CleanRoom"));
		assertEquals(expected, request);
	}
}