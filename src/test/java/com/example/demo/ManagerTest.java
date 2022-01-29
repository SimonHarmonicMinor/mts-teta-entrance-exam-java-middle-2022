package com.example.demo;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ManagerTest {

	Manager mgr = null;
	
	@BeforeEach
	void before() {
		// start clean
		mgr = new Manager();
	}
	
	@Test
	void test_bad_format() {
		assertEquals("CREATED", mgr.process("ali				 CREATE_TASK  z"));
		assertEquals("WRONG_FORMAT", mgr.process("ali CREATE_TASK z x"));
		assertEquals("WRONG_FORMAT", mgr.process("CREATE_TASK z x"));
		assertEquals("WRONG_FORMAT", mgr.process("bob PRINTE_TASK 285"));
	}
	
	@Test
	void test_create() {
		assertEquals("CREATED", mgr.process("ali CREATE_TASK z"));
		assertEquals("CREATED", mgr.process("bob CREATE_TASK x"));
		assertEquals("ERROR", mgr.process("ali CREATE_TASK x"));
		assertEquals("ERROR", mgr.process("bob CREATE_TASK x"));
		assertEquals("CREATED", mgr.process("bob     CREATE_TASK write"));
	}

	@Test
	void test_states() {
		assertEquals("CREATED", mgr.process("ali CREATE_TASK z"));
		
		assertEquals("CLOSED", mgr.process("ali CLOSE_TASK z"));
		assertEquals("REOPENED", mgr.process("ali REOPEN_TASK z"));
		
		assertEquals("ERROR", mgr.process("ali REOPEN_TASK z"));
		assertEquals("CLOSED", mgr.process("ali CLOSE_TASK z"));
		assertEquals("REOPENED", mgr.process("ali REOPEN_TASK z"));
		assertEquals("TASKS [z]", mgr.process("me LIST_TASK ali"));

		assertEquals("ERROR", mgr.process("ali DELETE_TASK z"));
		assertEquals("CLOSED", mgr.process("ali CLOSE_TASK z"));
		assertEquals("DELETED", mgr.process("ali DELETE_TASK z"));
		assertEquals("TASKS []", mgr.process("ali LIST_TASK ali"));
	}

	@Test
	void test_access() {
		assertEquals("CREATED", mgr.process("ali CREATE_TASK x"));
		assertEquals("CREATED", mgr.process("ali CREATE_TASK y"));
		assertEquals("CREATED", mgr.process("bob CREATE_TASK z"));

		assertEquals("ERROR", mgr.process("ali CREATE_TASK z"));
		assertEquals("ACCESS_DENIED", mgr.process("ali REOPEN_TASK z"));
		assertEquals("ACCESS_DENIED", mgr.process("ali CLOSE_TASK z"));
		assertEquals("ACCESS_DENIED", mgr.process("ali DELETE_TASK z"));
		
		assertEquals("CLOSED", mgr.process("bob CLOSE_TASK z"));
		assertEquals("ACCESS_DENIED", mgr.process("ali REOPEN_TASK z"));
		assertEquals("ACCESS_DENIED", mgr.process("ali CLOSE_TASK z"));
		assertEquals("ACCESS_DENIED", mgr.process("ali DELETE_TASK z"));
		
		assertEquals("DELETED", mgr.process("bob DELETE_TASK z"));
		assertEquals("ERROR", mgr.process("ali REOPEN_TASK z"));
		assertEquals("ERROR", mgr.process("ali CLOSE_TASK z"));
		assertEquals("ERROR", mgr.process("ali DELETE_TASK z"));
	}

	@Test
	void test_reuse() {
		assertEquals("CREATED", mgr.process("ali CREATE_TASK z"));
		assertEquals("ACCESS_DENIED", mgr.process("ALI CLOSE_TASK z"));
		assertEquals("CLOSED", mgr.process("ali CLOSE_TASK z"));
		assertEquals("TASKS [z]", mgr.process("me LIST_TASK ali"));
		assertEquals("DELETED", mgr.process("ali DELETE_TASK z"));

		assertEquals("CREATED",  mgr.process("bob CREATE_TASK z"));
		assertEquals("TASKS []", mgr.process("me LIST_TASK ali"));
		assertEquals("TASKS [z]", mgr.process("me LIST_TASK bob"));
	}
}
