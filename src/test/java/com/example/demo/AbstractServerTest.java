package com.example.demo;

import com.example.demo.server.Server;
import com.example.demo.task.TaskRepository;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import static com.example.demo.DemoApplication.initController;

public abstract class AbstractServerTest {

	private static Server server;
	private static final TaskRepository repo = new TaskRepository();

	@BeforeAll
	static void beforeAll() throws Exception {
		server = new Server(initController(repo));
		server.start();
	}

	@AfterAll
	static void afterAll() throws Exception {
		server.stop();
	}

	@AfterEach
	void afterEach() {
		repo.reset();
	}

	protected String sendMessage(String msg) {
		try (final var clientSocket = new Socket("localhost", 9090)) {
			final var out = new PrintWriter(clientSocket.getOutputStream(), true);
			final var in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
			out.println(msg);
			return in.readLine();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
}
