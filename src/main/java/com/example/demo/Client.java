package com.example.demo;

import Models.Commands;
import Models.Task;
import Models.User;

import java.io.*;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class Client {
    private static Socket clientSocket;
    private static BufferedReader reader;
    private static BufferedReader in;
    private static BufferedWriter out;
    private static Commands commands;
    static final private Map<String, Task> tasks = new HashMap<>();
    static final private Map<String, User> users = new HashMap<>();

    public static void main(String[] args) {
        try {
            try {
                while (true) {
                    commands = new Commands();
                    clientSocket = new Socket("localhost", 9090);
                    reader = new BufferedReader(new InputStreamReader(System.in));
                    in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                    out = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));
                    System.out.println("Enter command");
                    String word = reader.readLine();
                    System.out.println(word);
                    out.write(word + "\n");
                    out.flush();
                    if (word.equals("Quit"))
                        break;
                    String[] command = word.split(" ");
                    if (command.length != 3) {
                        System.out.println(commands.getWrongFormat());
                    }
                    String userName = command[0];
                    String procedure = command[1];
                    String taskName = command[2];
                    String userTask = command[2];

                    switch (procedure) {
                        case "CREATE_TASK":
                            User user = User.getUser(users, userName);
                            Task.createTask(tasks, user, taskName);
                            System.out.println(commands.getCreated());
                            break;
                        case "DELETE_TASK":
                            Task.deleteTask(tasks, userName, taskName);
                            System.out.println(commands.getDeleted());
                            break;
                        case "CLOSE_TASK":
                            Task.closeTask(tasks, userName, taskName);
                            System.out.println(commands.getClosed());
                            break;
                        case "REOPEN_TASK":
                            Task.reopenTask(tasks, userName, taskName);
                            System.out.println(commands.getReopened());
                            break;
                        case "LIST_TASK":
                            Task.getListTasks(users, userTask);
                            break;
                        default:
                            System.out.println(commands.getError());
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                System.out.println("Client is closed...");
                clientSocket.close();
                in.close();
                out.close();
            }
        } catch (IOException e) {
            System.err.println(e);
        }
    }
}