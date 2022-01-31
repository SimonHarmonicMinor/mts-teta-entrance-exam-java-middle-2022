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
            while (clientConnection.isConnected()) {
                clientCommand = scanner.nextLine();
                getCommand(clientCommand);
                result = clientReader.readLine();
                System.out.println(result);
                if (clientCommand.contains("CONNECTION")) {
                    break;
                }
            }
                break;
        }
        stopClientConnection();
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
        clientWriter.write(command);
        clientWriter.newLine();
        clientWriter.flush();
    }

    public void stopClientConnection() throws IOException {
        clientConnection.close();
    }
}
