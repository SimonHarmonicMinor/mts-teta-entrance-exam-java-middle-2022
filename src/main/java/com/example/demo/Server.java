package com.example.demo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    private static final Logger LOG = LoggerFactory.getLogger(Server.class);

    private ServerSocket serverSocket;

    public void start() throws IOException {
        serverSocket = new ServerSocket(9090);
        TasksPlanner tasksPlanner = new TasksPlanner();
        Thread serverThread = new Thread(() -> {
            while (true) {
                try {
                    Socket connection = serverSocket.accept();
                    try (
                            BufferedReader serverReader = new BufferedReader(
                                    new InputStreamReader(connection.getInputStream()));
                            Writer serverWriter = new BufferedWriter(
                                    new OutputStreamWriter(connection.getOutputStream()));
                    ) {
                        String line = serverReader.readLine();
                        LOG.debug("Request captured: " + line);
                        //В реализации фич в ответе пишется результат выполнения команды
                        serverWriter.write(planner(line, tasksPlanner));
                        serverWriter.flush();
                    }
                } catch (Exception e) {
                    LOG.error("Error during request proceeding", e);
                    break;
                }
            }
        });
        serverThread.setDaemon(true);
        serverThread.start();
    }

    private String planner(String inputline, TasksPlanner tasksPlanner) {
        String[] lineParse = inputline.split(" ");
        String userName, command, taskName, arg = "", result = "";
        try {
            userName = lineParse[0];
            command = lineParse[1];
            taskName = lineParse[2];
            try {
                arg = " " + lineParse[3];
            } catch (IndexOutOfBoundsException ignored) {
            }
            switch (command) {
                case "CREATE_TASK":
                    return tasksPlanner.create_task(userName, taskName) + arg;
                case "DELETE_TASK":
                    return tasksPlanner.delete_task(userName, taskName) + arg;
                case "CLOSE_TASK":
                    return tasksPlanner.close_task(userName, taskName) + arg;
                case "REOPEN_TASK":
                    return tasksPlanner.reopen_task(userName, taskName) + arg;
                case "LIST_TASK":
                    return tasksPlanner.get_list_task(taskName) + arg;
                default:
                    return "WRONG_FORMAT";
            }
        } catch (IndexOutOfBoundsException e) {
            return "WRONG_FORMAT";
        }
    }

    public void stop() throws Exception {
        serverSocket.close();
    }
}