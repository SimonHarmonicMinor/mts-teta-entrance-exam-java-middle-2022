package com.costa.socket.server;

import com.costa.socket.server.dao.TaskDao;
import com.costa.socket.server.dao.UserDao;
import com.costa.socket.server.dao.impl.TaskDaoImpl;
import com.costa.socket.server.dao.impl.UserDaoImpl;
import com.costa.socket.server.daoservice.TaskService;
import com.costa.socket.server.daoservice.UserService;
import com.costa.socket.server.daoservice.impl.TaskServiceImpl;
import com.costa.socket.server.daoservice.impl.UserServiceImpl;
import com.costa.socket.server.model.ResultStatus;
import com.costa.socket.server.service.CommandHandler;
import com.costa.socket.server.service.Validator;
import com.costa.socket.server.service.impl.BaseCommandHandler;
import com.costa.socket.server.service.impl.CommandValidator;
import com.costa.socket.server.service.impl.ServerRunner;
import com.costa.socket.server.service.impl.TaskHandlerFactory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * Test Blank for the integration test
 */
public class AbstractServerTest {
    private static UserDao userDao;
    private static UserService userService;
    private static TaskDao taskDao;
    private static TaskService taskService;
    private static Validator validator;
    private static TaskHandlerFactory taskHandlerFactory;
    private static CommandHandler<ResultStatus> commandHandler;

    private static Socket clientSocket;
    private static PrintWriter out;
    private static BufferedReader in;

    @BeforeAll
    static void beforeAll() {
        userDao = new UserDaoImpl();
        userService = new UserServiceImpl(userDao);
        taskDao = new TaskDaoImpl();
        taskService = new TaskServiceImpl(taskDao, userService);
        validator = new CommandValidator(taskService);
        taskHandlerFactory = new TaskHandlerFactory(validator, taskService);
        commandHandler = new BaseCommandHandler(validator, taskHandlerFactory);
        new ServerRunner(commandHandler).start();
    }

    @BeforeEach
    void beforeEach() throws Exception {
        //if necessary, can load the settings from the file server.properties
        clientSocket = new Socket("localhost", 9999);
        out = new PrintWriter(clientSocket.getOutputStream(), true);
        in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
    }

    @AfterEach
    void afterEach() throws Exception {
        in.close();
        out.close();
        clientSocket.close();
    }

    protected String sendMessage(String msg) {
        out.println(msg);
        try {
            return in.readLine();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
