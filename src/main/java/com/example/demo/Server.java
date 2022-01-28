package com.example.demo;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Arrays;
import java.util.Optional;

import com.example.demo.models.Request;
import com.example.demo.models.Task;
import com.example.demo.repositories.TaskRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Server {

    private static final Logger LOG = LoggerFactory.getLogger(Server.class);

    private ServerSocket serverSocket;

    private TaskRepository tasks;

    public void start() throws IOException {
        tasks = new TaskRepository();
        serverSocket = new ServerSocket(9090);
        Thread serverThread = new Thread(this::runServer);
        serverThread.setDaemon(true);
        serverThread.start();
    }

    public void stop() throws Exception {
        serverSocket.close();
    }

    private void runServer() {
        while (true) {
            try {
                Socket connection = serverSocket.accept();
                try (
                        BufferedReader serverReader = new BufferedReader(
                                new InputStreamReader(connection.getInputStream()));
                        Writer serverWriter = new BufferedWriter(
                                new OutputStreamWriter(connection.getOutputStream()))
                ) {
                    String line = serverReader.readLine();
                    LOG.debug("Request captured: " + line);
                    var request = parseRequest(line);
                    request.ifPresentOrElse(req -> {
                        try {
                            serverWriter.write(processRequest(req));
                            serverWriter.flush();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }, () -> {
                        try {
                            serverWriter.write(Responses.WRONG_FORMAT);
                            serverWriter.flush();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    });
                }
            } catch (Exception e) {
                LOG.error("Error during request proceeding", e);
                break;
            }
        }
    }

    private String processRequest(Request request) {
        switch (request.getCommand()) {
            case Commands.CREATE_TASK:
                if (tasks.addTask(new Task(request.getArg(), request.getUser()))) return Responses.CREATED;
                return Responses.ERROR;
            case Commands.LIST_TASK:
                return "TASKS " + Arrays.toString(tasks.getTasksByUser(request.getArg()));
            case Commands.DELETE_TASK:
                var taskToDelete = tasks.getTaskByName(request.getArg());
                if (taskToDelete.isEmpty()) return Responses.ERROR;
                if (!checkPermission(request.getUser(), taskToDelete.get())) return Responses.ACCESS_DENIED;
                if (taskToDelete.get().delete() && tasks.updateTask(taskToDelete.get())) return Responses.DELETED;
                return Responses.ERROR;
            case Commands.CLOSE_TASK:
                var taskToClose = tasks.getTaskByName(request.getArg());
                if (taskToClose.isEmpty()) return Responses.ERROR;
                if (!checkPermission(request.getUser(), taskToClose.get())) return Responses.ACCESS_DENIED;
                if (taskToClose.get().close() && tasks.updateTask(taskToClose.get())) return Responses.CLOSED;
                return Responses.ERROR;
            case Commands.REOPEN_TASK:
                var taskToReopen = tasks.getTaskByName(request.getArg());
                if (taskToReopen.isEmpty()) return Responses.ERROR;
                if (!checkPermission(request.getUser(), taskToReopen.get())) return Responses.ACCESS_DENIED;
                if (taskToReopen.get().reopen() && tasks.updateTask(taskToReopen.get())) return Responses.REOPENED;
                return Responses.ERROR;
            default:
                return Responses.ERROR;
        }
    }

    private boolean checkPermission(String user, Task task) {
        return task.getUser().contentEquals(user);
    }

    private Optional<Request> parseRequest(String command) {
        String[] parts = Arrays.stream(command.split("\\s")).filter(s -> !s.isEmpty()).toArray(String[]::new);
        if (parts.length != 3) return Optional.empty();
        if (!Commands.COMMANDS.contains(parts[1])) return Optional.empty();
        return Optional.of(new Request(parts));
    }
}