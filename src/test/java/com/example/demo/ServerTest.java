package com.example.demo;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ServerTest extends AbstractServerTest {

	@Test
	@DisplayName("General test with README examples")
	void test1() {
		String response = sendMessage("VASYA CREATE_TASK CleanRoom");
		assertEquals("CREATED", response);

		response = sendMessage("PETYA DELETE_TASK CleanRoom");
		assertEquals("ACCESS_DENIED", response);

		response = sendMessage("PETYA CREATE_TASK Task1");
		assertEquals("CREATED", response);

		response = sendMessage("PETYA CREATE_TASK Task2");
		assertEquals("CREATED", response);

		response = sendMessage("VASYA LIST_TASK PETYA");
		assertEquals("TASKS [Task1, Task2]", response);

		response = sendMessage("VASYA CREATE_TASK CleanRoom");
		assertEquals("ERROR", response);
	}

	@Test
	@DisplayName("Should return ERROR on deleteing none-existing task")
	void test2() {
		final var response = sendMessage("PETYA DELETE_TASK CleanRoom");
		assertEquals("ERROR", response);
	}

	@Test
	@DisplayName("Should return WRONG_FORMAT on missing arguments")
	void test3() {
		var response = sendMessage("VASYA CREATE_TASK   \n\t");
		assertEquals("WRONG_FORMAT", response);

		response = sendMessage("");
		assertEquals("WRONG_FORMAT", response);
	}

	@Test
	@DisplayName("Should delete only closed task")
	void test4() {
		String response = sendMessage("VASYA CREATE_TASK CleanRoom");
		assertEquals("CREATED", response);

		response = sendMessage("VASYA DELETE_TASK CleanRoom");
		assertEquals("ACCESS_DENIED", response);

		response = sendMessage("VASYA CLOSE_TASK CleanRoom");
		assertEquals("CLOSED", response);

		response = sendMessage("VASYA DELETE_TASK CleanRoom");
		assertEquals("DELETED", response);

		response = sendMessage("VASYA DELETE_TASK CleanRoom");
		assertEquals("ACCESS_DENIED", response);
	}

	@Test
	@DisplayName("Should close only open task")
	void test5() {
		String response = sendMessage("VASYA CREATE_TASK CleanRoom");
		assertEquals("CREATED", response);

		response = sendMessage("VASYA CLOSE_TASK CleanRoom");
		assertEquals("CLOSED", response);

		response = sendMessage("VASYA CLOSE_TASK CleanRoom");
		assertEquals("ACCESS_DENIED", response);

		response = sendMessage("VASYA REOPEN_TASK CleanRoom");
		assertEquals("REOPENED", response);

		response = sendMessage("VASYA CLOSE_TASK CleanRoom");
		assertEquals("CLOSED", response);
	}

	@Test
	@DisplayName("Should re-open only closed task")
	void test6() {
		String response = sendMessage("VASYA CREATE_TASK CleanRoom");
		assertEquals("CREATED", response);

		response = sendMessage("VASYA REOPEN_TASK CleanRoom");
		assertEquals("ACCESS_DENIED", response);

		response = sendMessage("VASYA CLOSE_TASK CleanRoom");
		assertEquals("CLOSED", response);

		response = sendMessage("VASYA DELETE_TASK CleanRoom");
		assertEquals("DELETED", response);

		response = sendMessage("VASYA REOPEN_TASK CleanRoom");
		assertEquals("ACCESS_DENIED", response);
	}

	@Test
	@DisplayName("Should return empty task list")
	void test7() {
		String response = sendMessage("VASYA LIST_TASK PETYA");
		assertEquals("TASKS []", response);
	}

  @Test
  @DisplayName("Should filter task list by user")
  void test8() {
    sendMessage("VASYA CREATE_TASK CleanVasyaRoom");
    sendMessage("PETYA CREATE_TASK CleanPetyaRoom");
    String response = sendMessage("VITYA LIST_TASK PETYA");
    assertEquals("TASKS [CleanPetyaRoom]", response);
  }

  @Test
  @DisplayName("Should filter out deleted tasks")
  void test9() {
    sendMessage("VASYA CREATE_TASK CleanRoom");
    sendMessage("VASYA CREATE_TASK PrepareSomeFood");
    sendMessage("VASYA CLOSE_TASK CleanRoom");
    String response = sendMessage("VITYA LIST_TASK VASYA");
    assertEquals("TASKS [CleanRoom, PrepareSomeFood]", response);

    sendMessage("VASYA DELETE_TASK CleanRoom");
    response = sendMessage("VITYA LIST_TASK VASYA");
    assertEquals("TASKS [PrepareSomeFood]", response);
  }
}