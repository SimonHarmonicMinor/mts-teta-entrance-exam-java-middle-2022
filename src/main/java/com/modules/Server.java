package com.modules;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Server {

    private static final Logger LOG = LoggerFactory.getLogger(Server.class);
    private static final List<String> commands = Arrays.asList("CREATE_TASK", "DELETE_TASK", "CLOSE_TASK",
            "REOPEN_TASK", "LIST_TASK");
    private ServerSocket serverSocket;

    public void start() throws IOException {
        serverSocket = new ServerSocket(9090);
        List<Task> tasks = new ArrayList<>();
        Thread serverThread = new Thread(() -> {
            try {
                Socket connection = serverSocket.accept();
                try (
                        BufferedReader serverReader = new BufferedReader(
                                new InputStreamReader(connection.getInputStream()));
                        Writer serverWriter = new BufferedWriter(
                                new OutputStreamWriter(connection.getOutputStream()))
                ) {
                    while (true) {
                        LOG.debug("server start");
                        String line = serverReader.readLine();
                        LOG.debug("Request captured: " + line);
                        if (line == null) {
                            continue;
                        }
                        String[] lineArgs = line.split(" ");
                        if (lineArgs.length < 3 || lineArgs.length > 4) {
                            serverWriter.write("WRONG_FORMAT" + "\n");
                            serverWriter.flush();
                            continue;
                        }
                        String userName = lineArgs[0];
                        String command = lineArgs[1];
                        String name = lineArgs[2];
                        String arg;
                        // По задаче у запроса может быть 4 аргумента,
                        // но назначение четвертого аргумента не указано
                        if (lineArgs.length == 4) {
                            arg = lineArgs[3];
                        }
                        if (!commands.contains(command)) {
                            serverWriter.write("WRONG_FORMAT" + "\n");
                            serverWriter.flush();
                            continue;
                        }
                        Task chosenTask = new Task();
                        for (Task task : tasks) {
                            if (task.getName().equals(name)) {
                                chosenTask = task;
                            }
                        }
                        switch (command) {
                            case "CREATE_TASK":
                                if (chosenTask.getName() == null) {
                                    tasks.add(new Task(userName, "CREATED", name));
                                    serverWriter.write("CREATED" + "\n");
                                    serverWriter.flush();
                                } else if (!chosenTask.getAuthor().equals(userName)) {
                                    serverWriter.write("ACCESS_DENIED" + "\n");
                                    serverWriter.flush();
                                } else {
                                    serverWriter.write("ERROR" + "\n");
                                    serverWriter.flush();
                                }
                                continue;
                            case "DELETE_TASK":
                                if (chosenTask.getName() != null) {
                                    if (chosenTask.getAuthor().equals(userName)) {
                                        if (chosenTask.getStatus().equals("CLOSED")) {
                                            tasks.remove(chosenTask);
                                            serverWriter.write("DELETED" + "\n");
                                            serverWriter.flush();
                                        } else {
                                            serverWriter.write("ERROR" + "\n");
                                            serverWriter.flush();
                                        }
                                    } else {
                                        serverWriter.write("ACCESS_DENIED" + "\n");
                                        serverWriter.flush();
                                    }
                                } else {
                                    serverWriter.write("ERROR" + "\n");
                                    serverWriter.flush();
                                }
                                continue;
                            case "CLOSE_TASK":
                                if (chosenTask.getName() != null) {
                                    if (chosenTask.getAuthor().equals(userName)) {
                                        if (chosenTask.getStatus().equals("CREATED")) {
                                            chosenTask.setStatus("CLOSED");
                                            serverWriter.write("CLOSED" + "\n");
                                            serverWriter.flush();
                                        } else {
                                            serverWriter.write("ERROR" + "\n");
                                            serverWriter.flush();
                                        }
                                    } else {
                                        serverWriter.write("ACCESS_DENIED" + "\n");
                                        serverWriter.flush();
                                    }
                                } else {
                                    serverWriter.write("ERROR" + "\n");
                                    serverWriter.flush();
                                }
                                continue;
                            case "REOPEN_TASK":
                                if (chosenTask.getName() != null) {
                                    if (chosenTask.getAuthor().equals(userName)) {
                                        if (chosenTask.getStatus().equals("CLOSED")) {
                                            chosenTask.setStatus("CREATED");
                                            serverWriter.write("REOPENED" + "\n");
                                            serverWriter.flush();
                                        } else {
                                            serverWriter.write("ERROR" + "\n");
                                            serverWriter.flush();
                                        }
                                    } else {
                                        serverWriter.write("ACCESS_DENIED" + "\n");
                                        serverWriter.flush();
                                    }
                                } else {
                                    serverWriter.write("ERROR" + "\n");
                                    serverWriter.flush();
                                }
                                continue;
                            case "LIST_TASK":
                                List<String> list = new ArrayList<>();
                                for (Task task : tasks) {
                                    if (task.getAuthor().equals(name)) {
                                        list.add(task.getName());
                                    }
                                }
                                if (list.size() == 0) {
                                    serverWriter.write("ERROR" + "\n");
                                    serverWriter.flush();
                                } else {
                                    serverWriter.write("TASKS " + list + "\n");
                                    serverWriter.flush();
                                }
                                continue;
                        }
                        LOG.debug("End of request");
                    }
                } catch (Exception e) {
                    LOG.error("Error during request proceeding", e);
                }
            } catch (IOException e1) {
                LOG.error("Error during request proceeding", e1);
            }
        });
        serverThread.setDaemon(true);
        serverThread.start();
        LOG.trace("serverThread started");
    }

    public void stop() throws Exception {
        serverSocket.close();
        LOG.trace("serverSocket closed");
    }
}