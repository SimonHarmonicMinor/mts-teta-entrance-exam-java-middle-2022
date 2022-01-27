package com.costa.client;

import com.costa.client.controller.ViewController;
import com.costa.client.service.ClientConnection;
import com.costa.client.view.ClientView;
import com.costa.core.config.BaseNetworkConfig;
import com.costa.util.config.ConfigLoader;

import javax.swing.*;

public class ClientApplication {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            ConfigLoader.load(BaseNetworkConfig.class, "client.properties");
            ViewController controller = new ViewController(new ClientView());
            new ClientConnection(controller).connect();
        });
    }
}
