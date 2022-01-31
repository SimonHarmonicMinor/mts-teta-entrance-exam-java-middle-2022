package com.example.demo.services.task;

import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;

public class TaskService extends Thread {

    private static final Logger LOG = LoggerFactory.getLogger(TaskService.class);

    private ServerSocket serverSocket;

    private TaskService() {
    }

    public TaskService(@NotNull ServerSocket serverSocket) {
        this.serverSocket = serverSocket;
    }

    @Override
    public void interrupt() {
        try {
            serverSocket.close();
        } catch (IOException ignored) {
        } finally {
            super.interrupt();
        }
    }

    @Override
    public void run() {
        while (!serverSocket.isClosed()) {
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

                    String response = new TaskProcessing(line).processingRequest();
                    serverWriter.write(response);
                    LOG.debug("Response captured: " + response);

                    serverWriter.flush();
                }
            } catch (Exception e) {
                if (!(e instanceof SocketException && isInterrupted()))
                    LOG.error("Error during request proceeding", e);

                break;
            }
        }
    }

}