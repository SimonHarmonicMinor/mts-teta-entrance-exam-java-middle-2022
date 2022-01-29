package com.costa.socket.server.service.impl;

import com.costa.core.config.BaseNetworkConfig;
import com.costa.core.listener.ConnectionListener;
import com.costa.core.service.SocketConnection;
import com.costa.socket.server.model.ResultStatus;
import com.costa.socket.server.service.CommandHandler;
import com.costa.util.config.ConfigLoader;
import com.costa.socket.server.listener.ServerConnectionListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.ServerSocket;

/**
 * Simple runner for the socket server
 */
public class ServerRunner {
    private static final Logger LOG = LoggerFactory.getLogger(ServerRunner.class);
    private final ConnectionListener listener;
    private SocketConnection socketConnection;

    public ServerRunner(CommandHandler<ResultStatus> commandHandler) {
        this.listener = new ServerConnectionListener<>(commandHandler);
    }

    public void start() {
        ConfigLoader.load(BaseNetworkConfig.class, "server.properties");
        Thread serverThread = new Thread(() -> {
            try (ServerSocket serverSocket = new ServerSocket(BaseNetworkConfig.getPort())) {
                LOG.debug("Server running...");
                acceptSocket(serverSocket);
            } catch (IOException e) {
                LOG.error("Some problem with server", e);
            }
        });

        serverThread.start();
    }

    private void acceptSocket(ServerSocket serverSocket) {
        while (true) {
            try {
                socketConnection = new SocketConnection(listener, serverSocket.accept());
                socketConnection.start();
            } catch (IOException e) {
                LOG.error("Error during request proceeding", e);
                break;
            }
        }
    }

    public void closeConnection(){
        socketConnection.disconnect();
    }
}
