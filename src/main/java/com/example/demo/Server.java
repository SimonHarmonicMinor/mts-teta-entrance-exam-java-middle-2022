package com.example.demo;

import com.example.demo.services.task.TaskService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;

import static com.example.demo.Configure.buildPort;
import static com.example.demo.Configure.buildSocketTimeout;

public class Server {

    private static final Logger LOG = LoggerFactory.getLogger(Server.class);

    private TaskService taskThread;

    public void start() throws IOException {
        LOG.debug("Starting server...");
        ServerSocket serverSocket = new ServerSocket(buildPort(), -1, InetAddress.getLocalHost());
        serverSocket.setSoTimeout(buildSocketTimeout());
        taskThread = new TaskService(serverSocket);
        taskThread.setDaemon(true);
        taskThread.start();
    }

    public void stop() {
        LOG.debug("Stopping server...");
        taskThread.interrupt();
    }

}