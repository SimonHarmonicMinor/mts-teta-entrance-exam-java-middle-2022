package com.costa.socket.server.listener;

import com.costa.core.listener.ConnectionListener;
import com.costa.core.model.SocketConnection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Listener for server connection handler
 */
public class ServerConnectionListener implements ConnectionListener {
    private static final Logger LOG = LoggerFactory.getLogger(ServerConnectionListener.class);

    @Override
    public void onDisconnect(SocketConnection connection) {
        connection.sendMessage("disconnected from server");
    }

    @Override
    public void onException(SocketConnection connection, Exception e) {
        LOG.error("Exception with connection {}", connection, e);
    }

    @Override
    public void onReceiveMessage(SocketConnection connection, String message) {
        connection.sendMessage("With Server " + message);
    }

    @Override
    public void onConnectionSuccess(SocketConnection connection) {
        connection.sendMessage("Connected to the server");
    }
}
