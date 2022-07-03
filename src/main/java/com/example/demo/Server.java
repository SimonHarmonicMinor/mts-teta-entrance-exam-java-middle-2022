package com.example.demo;

import com.example.demo.controller.TaskController;
import com.example.demo.data.TaskTrackerData;
import com.example.demo.service.TaskService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    private static final Logger LOG = LoggerFactory.getLogger(Server.class);

    private ServerSocket serverSocket;

    public void start() throws IOException {
        serverSocket = new ServerSocket(9090);

        //когда нет автосвязывания..
        TaskTrackerData data = new TaskTrackerData();
        TaskService service = new TaskService(data);
        TaskController controller = new TaskController(service);

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
                        String responseLine = controller.receiveRequest(line);
                        LOG.debug("Response : " + responseLine);
                        serverWriter.write(responseLine);
                        serverWriter.flush();
                    }
                } catch (Exception e) {
                    LOG.error("Error during request proceeding", e);
                    break;
                }
            }
        });
        serverThread.start();
    }

    public void stop() throws Exception {
        serverSocket.close();
    }
}