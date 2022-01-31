package com.example.demo;

import com.example.demo.model.ClientData;
import com.example.demo.model.Task;
import com.example.demo.model.enums.Command;
import com.example.demo.model.enums.Result;
import com.example.demo.model.enums.Status;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Server {

    private static final Logger LOG = LoggerFactory.getLogger(Server.class);

    private ServerSocket serverSocket;

    final List<String> users = Arrays.asList("Borisov", "Valeev", "Baruh");
    final List<Task> tasks = new ArrayList<>();

    public void start() throws IOException {
        serverSocket = new ServerSocket(9090);
        final Thread serverThread = new Thread(() -> {
            while (!serverSocket.isClosed()) {
                try {
                    final Socket connection = serverSocket.accept();
                    try (final BufferedReader serverReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                         final PrintWriter serverWriter = new PrintWriter(new BufferedWriter(new OutputStreamWriter(connection.getOutputStream())), true)) {

                        String line;
                        while ((line = serverReader.readLine()) != null) {
                            LOG.debug("Request captured: {}", line);

                            final String result = validationAndActionWithTask(line);

                            serverWriter.println(result);
                            serverWriter.flush();
                        }
                    } catch (Exception e) {
                        LOG.error("Error during request proceeding with message {}", e.getMessage(), e);
                        e.printStackTrace();
                    }
                } catch (Exception e) {
                    LOG.error("Server error: {} ", e.getMessage(), e);
                    e.printStackTrace();
                }
            }
        });
        serverThread.setDaemon(false);
        serverThread.start();
    }

    public void stop() throws Exception {
        serverSocket.close();
    }

    private String validationAndActionWithTask(final String line) {
        final String[] data = line.split(" ");
        if (data.length < 2 || data.length > 3) {
            LOG.warn("Illegal counts of arguments: {}", data.length);
            return Result.WRONG_FORMAT.toString();
        }

        final boolean isWrongCommand = Arrays.stream(Command.values()).noneMatch(x -> x.toString().equals(data[1]));
        if (isWrongCommand) {
            LOG.warn("Wrong command: {}", data[1]);
            return Result.WRONG_FORMAT.toString();
        }

        final ClientData clientData = new ClientData(data);
        if (!users.contains(clientData.getUserName())) {
            LOG.warn("Access denied with user name: {}", clientData.getUserName());
            return Result.ACCESS_DENIED.toString();
        }

        return executeTask(clientData);
    }

    private String executeTask(final ClientData clientData) {
        switch (clientData.getCommand()) {
            case CREATE_TASK:
                final boolean isTaskNotExist = tasks.stream()
                        .filter(x -> !x.getStatus().equals(Status.DELETED))
                        .noneMatch(x -> x.getTaskName().equals(clientData.getArg()));
                if (isTaskNotExist) {
                    final Task taskToCreate = new Task(clientData.getUserName(), clientData.getArg());
                    tasks.add(taskToCreate);
                    return Result.CREATED.toString();
                } else {
                    LOG.warn("Task already exist with name: {}", clientData.getArg());
                    return Result.ERROR.toString();
                }

            case CLOSE_TASK:
                final Task taskToClose = tasks.stream()
                        .filter(x -> x.getStatus().equals(Status.CREATED))
                        .filter(x -> x.getTaskName().equals(clientData.getArg()))
                        .filter(x -> x.getUserName().equals(clientData.getUserName()))
                        .findFirst().orElse(null);
                if (taskToClose != null) {
                    taskToClose.setStatus(Status.CLOSED);
                    return Result.CLOSED.toString();
                } else {
                    LOG.warn("Not found valid task to close: {}", clientData.getArg());
                    return Result.ERROR.toString();
                }

            case REOPEN_TASK:
                final Task taskToReopen = tasks.stream()
                        .filter(x -> x.getStatus().equals(Status.CLOSED))
                        .filter(x -> x.getTaskName().equals(clientData.getArg()))
                        .filter(x -> x.getUserName().equals(clientData.getUserName()))
                        .findFirst().orElse(null);
                if (taskToReopen != null) {
                    taskToReopen.setStatus(Status.CREATED);
                    return Result.REOPENED.toString();
                } else {
                    LOG.warn("Not found valid task to reopen: {}", clientData.getArg());
                    return Result.ERROR.toString();
                }

            case DELETE_TASK:
                final Task taskToDelete = tasks.stream()
                        .filter(x -> x.getStatus().equals(Status.CLOSED))
                        .filter(x -> x.getTaskName().equals(clientData.getArg()))
                        .filter(x -> x.getUserName().equals(clientData.getUserName()))
                        .findFirst().orElse(null);
                if (taskToDelete != null) {
                    taskToDelete.setStatus(Status.DELETED);
                    return Result.DELETED.toString();
                } else {
                    LOG.warn("Not found valid task to delete: {}", clientData.getArg());
                    return Result.ERROR.toString();
                }

            case LIST_TASK:
                return tasks.stream()
                        .filter(x -> !x.getStatus().equals(Status.DELETED))
                        .collect(Collectors.toList())
                        .toString();

            default:
                return Result.ERROR.toString();
        }
    }
}