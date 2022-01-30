package com.example.demo;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class Client {

    Socket clientConnection;

    BufferedReader clientReader;

    BufferedWriter clientWriter;

    Scanner scanner = new Scanner(System.in);

    String message, clientCommand, result;

    public void startClientConnection() throws IOException {
        clientConnection = new Socket("localhost", 9002);
        clientReader = new BufferedReader(
                new InputStreamReader(clientConnection.getInputStream()));
        clientWriter = new BufferedWriter(
                new OutputStreamWriter(clientConnection.getOutputStream()));
    }

    public void clientAction() throws IOException {
        while (true) {
            message = clientReader.readLine();
            System.out.println(message);
            clientCommand = scanner.nextLine();
            auth(clientCommand);
            message = clientReader.readLine();
            System.out.println(message);
            while (true) {
                clientCommand = scanner.nextLine();
                getCommand(clientCommand);
                result = clientReader.readLine();
                System.out.println(result);
                if (clientCommand.equals("EXIT")) {
                    break;
                }
            }
            if (clientCommand.equals("EXIT")) {
                break;
            }
        }
        clientConnection.close();
    }

    public void auth(String username) throws IOException {
        while (!username.equals(username.toUpperCase())) {
            System.out.println("WRONG_FORMAT");
            username = scanner.nextLine();
        }
        clientWriter.write(username);
        clientWriter.newLine();
        clientWriter.flush();
    }

    public void getCommand(String command) throws IOException {
        while (!checkFormat(command)) {
            System.out.println("WRONG_FORMAT");
            command = scanner.nextLine();
        }
        clientWriter.write(command);
        clientWriter.newLine();
        clientWriter.flush();
    }

    public boolean checkFormat(String clientCommand) {
        boolean format = false;
        if (clientCommand.equals("HELP") || clientCommand.equals("EXIT")) {
            format = true;
        } else {
            try {
                String[] command = clientCommand.split(" ");
                String firstWord = command[0];
                String secondWord = command[1];
                String thirdWord = command[2];

                if (secondWord.equalsIgnoreCase("LIST_TASK")) {
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
            } catch (ArrayIndexOutOfBoundsException e) {
                System.out.println("ERROR_UNKNOWN_COMMAND");
            }
        }
        return format;
    }

    public void stopClientConnection() throws IOException {
        clientConnection.close();
    }
}
