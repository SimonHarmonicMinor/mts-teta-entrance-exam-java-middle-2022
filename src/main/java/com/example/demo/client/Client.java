package com.example.demo.client;

import com.example.demo.core.enums.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.Socket;

public class Client {

  private static final Logger LOG = LoggerFactory.getLogger(Client.class);
  private final String DEFAULT_HOST = "127.0.0.1";
  private final int DEFAULT_PORT = 9090;
  private Socket clientSocket;
  private BufferedReader clientReader;
  private BufferedWriter clientWriter;

  public Client() {}

  public static void main(String[] args) throws IOException {
    Client client = new Client();
    client.start();
  }

  public void start() throws IOException {
    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
    String host = DEFAULT_HOST;
    int port = DEFAULT_PORT;
    System.out.println("Введите адрес сервера для подключения (по умолчанию 127.0.0.1):");
    String line1 = bufferedReader.readLine();
    if (!line1.isBlank()) {
      host = line1;
    }
    System.out.println("Введите порт для подключения (по умолчанию 9090):");
    String line2 = bufferedReader.readLine();
    if (isInteger(line2)) {
      if (Integer.parseInt(line2) > 1000) {
        port = Integer.parseInt(line2);
      }
    }
    this.connect(host, port);
    while (clientSocket.isConnected()) {
      System.out.println("Введите команду:");
      String request = bufferedReader.readLine();
      this.sendMessage(request);
      this.readMessage();
      if (request.equals("EXIT")) {
        System.out.println("Выполнен выход из программы");
        break;
      }
    }
  }

  public Result readMessage() throws IOException {
    System.out.println("Сервер ответил: " + clientReader.readLine());
    return null;
  }

  public Result sendMessage(String message) throws IOException {
    clientWriter.write(message + "\n");
    clientWriter.flush();
    return null;
  }

  public Socket getClientSocket() {
    return clientSocket;
  }

  private void connect(String host, int port) throws IOException {
    System.out.println("Попытка подключения к серверу " + host + ":" + port);
    this.clientSocket = new Socket(host, port);
    this.clientReader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
    this.clientWriter = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));
    System.out.println("Клиент подлючён к серверу " + host + ":" + port);
  }

  private boolean isInteger(String line) {
    if (line.length() == 0) {
      return false;
    }
    try {
      Integer.parseInt(line);
      return true;
    } catch (NumberFormatException numberFormatException) {
      return false;
    }
  }
}
