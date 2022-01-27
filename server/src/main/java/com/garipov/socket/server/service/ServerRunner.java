package com.garipov.socket.server.service;

import com.costa.core.config.BaseNetworkConfig;
import com.costa.core.listener.ConnectionListener;
import com.costa.core.model.SocketConnection;
import com.costa.util.config.ConfigLoader;
import com.garipov.socket.server.listener.ServerListener;
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

    public ServerRunner() {
        this.listener = new ServerListener();
    }

    public void start() {
        ConfigLoader.load(BaseNetworkConfig.class, "server.properties");

        try (ServerSocket serverSocket = new ServerSocket(BaseNetworkConfig.getPort())) {
            LOG.debug("Server running...");
            acceptSocket(serverSocket);
        } catch (IOException e) {
            LOG.error("Some problem with server", e);
        }
    }

    private void acceptSocket(ServerSocket serverSocket) {
        while (true) {
            try {
                SocketConnection socketConnection = new SocketConnection(listener, serverSocket.accept());
                socketConnection.start();
            } catch (IOException e) {
                LOG.error("Error during request proceeding", e);
                break;
            }
        }
    }
}
