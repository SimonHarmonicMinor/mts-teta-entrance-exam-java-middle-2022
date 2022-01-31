package com.example.demo;

import com.example.demo.controller.Controller;
import com.example.demo.service.TaskService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    private static final Logger LOG = LoggerFactory.getLogger(Server.class);
    private final Controller controller;

    private ServerSocket serverSocket;

    public Server(Controller controller) {
        this.controller = controller;
    }

    public void start() throws IOException {
        serverSocket = new ServerSocket(9090);
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
                        String request = serverReader.readLine();
                        LOG.debug("Request captured: " + request);
                        // В реализации по умолчанию в ответе пишется та же строка, которая пришла в запросе
                        serverWriter.write(controller.defineResponseByRequest(request));
                        serverWriter.flush();
                    }
                } catch (Exception e) {
                    LOG.error("Error during request proceeding", e);
                    break;
                }
            }
        });
        //serverThread.setDaemon(true);
        serverThread.start();
    }

    public void stop() throws Exception {
        serverSocket.close();
    }
}