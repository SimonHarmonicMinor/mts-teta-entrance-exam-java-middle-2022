package com.example.demo;

import com.example.demo.config.TaskConfig;
import com.example.demo.logic.TaskController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

class Server {

    private static final Logger LOG = LoggerFactory.getLogger(Server.class);

    private ServerSocket serverSocket;

    void start() throws IOException {
        TaskController controller = TaskConfig.createController();

        serverSocket = new ServerSocket(9090);
        Thread serverThread = new Thread(() -> {
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

                        String response = controller.handleRequest(line);

                        serverWriter.write(response);
                        serverWriter.flush();
                    }
                } catch (Exception e) {
                    LOG.error("Error during request proceeding", e);
                    break;
                }
            }
        });
        serverThread.setDaemon(true);
        serverThread.start();
    }

    void stop() throws Exception {
        serverSocket.close();
    }
}