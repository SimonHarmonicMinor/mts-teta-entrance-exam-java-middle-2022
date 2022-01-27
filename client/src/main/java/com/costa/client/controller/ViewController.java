package com.costa.client.controller;

import com.costa.client.view.ClientView;
import com.costa.core.model.SocketConnection;
import com.costa.util.StringUtil;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ViewController {
    private final ClientView clientView;
    private SocketConnection connection;

    public ViewController(ClientView clientView) {
        this.clientView = clientView;
        this.clientView.getFieldMessage().addActionListener(new MessageSenderListener());
    }

    public void printMessage(String message){
        JTextArea textArea = clientView.getTextArea();
        textArea.append(message + "\n");
        textArea.setCaretPosition(textArea.getDocument().getLength());
    }

    public void setConnection(SocketConnection  connection) {
        this.connection = connection;
    }

    class MessageSenderListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (!validateFields())
                return;

            String msg = clientView.getFieldMessage().getText();
            clientView.getFieldMessage().setText(null);
            connection.sendMessage(clientView.getFieldNick().getText() + " " + msg);
        }

        private boolean validateFields(){
            if (StringUtil.isEmpty(clientView.getFieldMessage().getText()))
                return false;

            if (StringUtil.isEmpty(clientView.getFieldNick().getText())) {
                printMessage("Please fill the username field!");
                return false;
            }

            return true;
        }
    }
}
