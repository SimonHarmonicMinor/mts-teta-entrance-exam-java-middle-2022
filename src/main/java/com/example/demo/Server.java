package com.example.demo;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Server {

    private static final Logger LOG = LoggerFactory.getLogger(Server.class);

    ServerSocket serverSocket;

    Socket connection;

    ClientParams clientParams;

    String clientName, result, clientRequest;

    ArrayList<String> userNames = new ArrayList<>();

    List<String> commandList = Arrays.asList("HELP", "EXIT", "CREATE_TASK", "DELETE_TASK", "CLOSE_TASK",
            "REOPEN_TASK", "LIST_TASK");

    HashMap<String, ClientParams> clientInfo = new HashMap<>();

    HashMap<String, String> taskList = new HashMap<>();

    public void start() throws IOException {
        serverSocket = new ServerSocket(9002);
        Thread serverThread = new Thread(() -> {
            while (!serverSocket.isClosed()) {
                try {
                    connection = serverSocket.accept();
                    try (
                            BufferedReader serverReader = new BufferedReader(
                                    new InputStreamReader(connection.getInputStream()));
                            BufferedWriter serverWriter = new BufferedWriter(
                                    new OutputStreamWriter(connection.getOutputStream()))
                    ) {
                        while (!connection.isClosed()) {
                            serverWriter.write("Введите имя (Формат: USERNAME):");
                            serverWriter.newLine();
                            serverWriter.flush();
                            clientName = serverReader.readLine();
                            if (!userNames.contains(clientName)) {
                                userNames.add(clientName);
                                clientParams = new ClientParams();
                                clientInfo.put(clientName, clientParams);
                            }
                            serverWriter.write("Введите команду (Формат: USERNAME COMMAND_NAME TaskName)");
                            serverWriter.newLine();
                            serverWriter.flush();
                            while (!connection.isClosed()) {
                                clientRequest = serverReader.readLine();
                                if (clientRequest.contains("CONNECTION CLOSE")) {
                                    result = "Выполняется выход.";
                                    serverWriter.write(result);
                                    serverWriter.newLine();
                                    serverWriter.flush();
                                    break;
                                } else {
                                    try {
                                        if (!clientRequest.isEmpty()) {
                                            String[] request = clientRequest.split(" ");
                                            String userName = request[0];
                                            String command = request[1];
                                            String taskName = request[2];
                                            if (!commandList.contains(command)) {
                                                while (!checkFormat(clientRequest)) {
                                                    result = "WRONG_FORMAT";
                                                    serverWriter.write(result);
                                                    serverWriter.newLine();
                                                    serverWriter.flush();
                                                    clientRequest = serverReader.readLine();
                                                }
                                            } else {
                                                if (command.equals("CREATE_TASK")) {
                                                    createTask(clientName, userName, taskName);
                                                } else if (command.equals("DELETE_TASK")) {
                                                    deleteTask(clientName, taskName);
                                                } else if (command.equals("CLOSE_TASK")) {
                                                    closeTask(clientName, taskName);
                                                } else if (command.equals("REOPEN_TASK")) {
                                                    reOpenTask(clientName, taskName);
                                                } else if (command.equals("LIST_TASK")) {
                                                    getListTask(userName, taskName);
                                                } else {
                                                    result = "ERROR_UNKNOWN_COMMAND";
                                                }
                                            }
                                        }
                                    } catch (ArrayIndexOutOfBoundsException e) {
                                        result = "ERROR_UNKNOWN_COMMAND";
                                    }
                                }
                                serverWriter.write(result);
                                serverWriter.newLine();
                                serverWriter.flush();
                            }
                            if (clientRequest.contains("CONNECTION CLOSE") || connection.isClosed()) {
                                break;
                            }
                        }
                    }
                } catch (SocketException ex) {
                    LOG.error("Error during closing socket proceeding", ex);
                } catch (NullPointerException ignored) {
                } catch (Exception e) {
                    LOG.error("Error during request proceeding", e);
                }
            }
        });
        serverThread.setDaemon(true);
        serverThread.start();
    }


    public void stop() throws Exception {
        serverSocket.close();
    }

    public void createTask(String clientName, String userName, String taskName) {
        if (userName.equals(clientName)) {
            clientParams = clientInfo.get(clientName);
            if (!taskList.containsKey(taskName)) {
                clientParams.createTaskInfo(taskName, "CREATED");
                taskList.put(taskName, userName);
                result = "CREATED";
            } else {
                result = "ERROR_TASK_NAME_ALREADY_EXIST";
            }
        } else {
            result = "ACCESS_DENIED";
        }
    }

    public void closeTask(String clientName, String taskName) {
        if (taskList.containsKey(taskName)) {
            if (taskList.get(taskName).equals(clientName)) {
                clientParams = clientInfo.get(clientName);
                if (clientParams.getTaskInfo().containsKey(taskName)
                        && clientParams.getTaskInfo().get(taskName).equals("CREATED")) {
                    clientParams.updateTaskInfo(taskName, "CLOSED");
                    result = "CLOSED";
                } else {
                    result = "ERROR_TASK_CURRENT_STAGE_SHOULD_BE_CREATED";
                }
            } else {
                result = "ACCESS_DENIED";
            }
        } else {
            result = "ERROR_TASK_DONT_EXIST";
        }
    }

    public void deleteTask(String clientName, String taskName) {
        if (taskList.containsKey(taskName)) {
            if (taskList.get(taskName).equals(clientName)) {
                clientParams = clientInfo.get(clientName);
                if (clientParams.getTaskInfo().containsKey(taskName)
                        && clientParams.getTaskInfo().get(taskName).equals("CLOSED")) {
                    clientParams.updateTaskInfo(taskName, "DELETED");
                    taskList.remove(taskName);
                    result = "DELETED";
                } else {
                    result = "ERROR_TASK_CURRENT_STAGE_SHOULD_BE_CLOSED";
                }
            } else {
                result = "ACCESS_DENIED";
            }
        } else {
            result = "ERROR_TASK_DONT_EXIST";
        }
    }

    public void reOpenTask(String clientName, String taskName) {
        if (taskList.containsKey(taskName)) {
            if (taskList.get(taskName).equals(clientName)) {
                clientParams = clientInfo.get(clientName);
                if (clientParams.getTaskInfo().containsKey(taskName)
                        && clientParams.getTaskInfo().get(taskName).equals("CLOSED")) {
                    clientParams.updateTaskInfo(taskName, "CREATED");
                    result = "REOPENED";
                } else {
                    result = "ERROR_TASK_CURRENT_STAGE_SHOULD_BE_CLOSED";
                }
            } else {
                result = "ACCESS_DENIED";
            }
        } else {
            result = "ERROR_TASK_DONT_EXIST";
        }
    }

    public void getListTask(String operatorName, String userName) {
        if (operatorName.equals(clientName)) {
            clientParams = clientInfo.get(userName);
            result = clientParams.getTaskInfo().keySet().toString();
        } else {
            result = "ERROR_WRONG_OPERATOR_NAME";
        }
    }

    public boolean checkFormat(String clientCommand) {

        boolean format = false;

        try {
            String[] command = clientCommand.split(" ");
            String firstWord = command[0];
            String secondWord = command[1];
            String thirdWord = command[2];

            if (secondWord.equalsIgnoreCase("LIST_TASK")
                    || secondWord.equalsIgnoreCase("CONNECTION")) {
                if (firstWord.equals(firstWord.toUpperCase()) || secondWord.equals(secondWord.toUpperCase())
                        || thirdWord.equals(thirdWord.toUpperCase())) {
                    format = true;
                }
            } else {
                if (firstWord.equals(firstWord.toUpperCase()) && secondWord.equals(secondWord.toUpperCase())
                        && thirdWord.matches("^([A-Z][a-z0-9]+)+")) {
                    format = true;
                }
            }
        } catch (ArrayIndexOutOfBoundsException ignored) {
        }
        return format;
    }
}