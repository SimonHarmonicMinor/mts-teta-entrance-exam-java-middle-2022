package com.costa.client.controller;

import com.costa.client.view.ClientView;
import com.costa.core.service.SocketConnection;
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
        clientView.getFieldMessage().setText(null);

        textArea.append(message + "\n");
        textArea.setCaretPosition(textArea.getDocument().getLength());
    }

    public void setConnection(SocketConnection  connection) {
        this.connection = connection;
    }

    class MessageSenderListener implements ActionListener {
        JTextField fieldMessage = clientView.getFieldMessage();
        JTextArea textArea = clientView.getTextArea();
        JTextField fieldNick  = clientView.getFieldNick();

        @Override
        public void actionPerformed(ActionEvent e) {
            if (fieldMessage.getText().equals("clear")){
                fieldMessage.setText(null);
                textArea.setText(null);
                return;
            }

            if (!validateFields()){
                fieldMessage.setText(null);
                return;
            }

            String message = fieldNick.getText() + " " + fieldMessage.getText();

            printMessage(message);
            connection.sendMessage(message);
        }

        private boolean validateFields() {
            if (StringUtil.isEmpty(fieldMessage.getText()))
                return false;

            if (StringUtil.isEmpty(fieldNick.getText())) {
                printMessage("Please fill the username field!");
                return false;
            }

            return true;
        }
    }
}
