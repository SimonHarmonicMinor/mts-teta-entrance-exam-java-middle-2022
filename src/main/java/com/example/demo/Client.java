package com.example.demo;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class Client {

    static Scanner scanner = new Scanner(System.in);

    static String message, clientCommand, result;

    public static void main(String[] args) throws IOException {
        Socket client = new Socket("127.0.0.1", 6060);
        BufferedReader clientReader = new BufferedReader(
                new InputStreamReader(client.getInputStream()));
        BufferedWriter clientWriter = new BufferedWriter(
                new OutputStreamWriter(client.getOutputStream()));
        while (true) {
            message = clientReader.readLine();
            System.out.println(message);
            clientCommand = scanner.nextLine();
            while (!clientCommand.equals(clientCommand.toUpperCase())) {
                System.out.println("WRONG_FORMAT");
                clientCommand = scanner.nextLine();
            }
            clientWriter.write(clientCommand);
            clientWriter.newLine();
            clientWriter.flush();

            while (true) {
                System.out.println("Введите команду (Формат: USERNAME COMMAND_NAME TaskName) (HELP - список команд):");
                clientCommand = scanner.nextLine();
                while (!checkFormat(clientCommand)) {
                    System.out.println("WRONG_FORMAT");
                    clientCommand = scanner.nextLine();
                }
                clientWriter.write(clientCommand);
                clientWriter.newLine();
                clientWriter.flush();
                result = clientReader.readLine();
                System.out.println(result);
            }
        }
    }

    public static boolean checkFormat(String clientCommand) {
        boolean format = false;
        if (clientCommand.equals("HELP")) {
            format = true;
        } else {
            try {
                String[] command = clientCommand.split(" ");
                String firstWord = command[0];
                String secondWord = command[1];
                String thirdWord = command[2];

                if (secondWord.equalsIgnoreCase("LIST_TASK")) {
                    if (firstWord.equals(firstWord.toUpperCase()) && secondWord.equals(secondWord.toUpperCase())
                            && thirdWord.equals(thirdWord.toUpperCase())) {
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
}
