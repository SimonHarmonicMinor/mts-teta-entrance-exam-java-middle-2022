package com.costa.client.service;

import com.costa.client.controller.ViewController;
import com.costa.core.config.BaseNetworkConfig;
import com.costa.core.listener.ConnectionListener;
import com.costa.core.model.SocketConnection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class ClientConnection implements ConnectionListener {
    private static final Logger LOG = LoggerFactory.getLogger(ClientConnection.class);
    private final ViewController controller;

    public ClientConnection(ViewController controller) {
        this.controller = controller;
    }

    public void connect() {
        try {
            new SocketConnection(this, BaseNetworkConfig.getHost(), BaseNetworkConfig.getPort()).start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onDisconnect(SocketConnection connection) {
        LOG.warn("Client with connect props {} disconnected", connection);
        controller.printMessage("Disconnected");
    }

    @Override
    public void onException(SocketConnection connection, Exception e) {
        controller.printMessage("An error has occurred");
    }

    @Override
    public void onReceiveMessage(SocketConnection connection, String message) {
        controller.printMessage(message);
    }

    @Override
    public void onConnectionSuccess(SocketConnection connection) {
        controller.setConnection(connection);
    }
}
