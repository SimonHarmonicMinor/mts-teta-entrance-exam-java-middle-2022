package com.costa.client.view;

import javax.swing.*;
import java.awt.*;

public class ClientView extends JFrame {
    private JTextArea textArea;
    private JTextField fieldNick;
    private JTextField fieldMessage;

    public ClientView() {
        initView();
    }

    private void initView() {
        textArea = new JTextArea();
        fieldNick = new JTextField();
        fieldMessage = new JTextField();

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(600, 400);
        setLocationRelativeTo(null);

        textArea.setEditable(false);
        textArea.setLineWrap(true);
        textArea.setAutoscrolls(true);

        JPanel panel = new JPanel(new BorderLayout());
        panel.add(fieldNick, BorderLayout.CENTER);
        panel.add(new JLabel("Username: "), BorderLayout.WEST);

        add(panel, BorderLayout.NORTH);
        add(textArea, BorderLayout.CENTER);
        add(fieldMessage, BorderLayout.SOUTH);

        setVisible(true);
    }

    public JTextArea getTextArea() {
        return textArea;
    }

    public JTextField getFieldNick() {
        return fieldNick;
    }

    public JTextField getFieldMessage() {
        return fieldMessage;
    }
}
