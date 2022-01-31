package com.modules;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

/**
 * Демо для показа работы функционала наглядно.
 * Все команды вводятся в консоль
 * Команда stop - останавливает процесс
 */
public class Client implements Runnable {
    private static final Logger LOG = LoggerFactory.getLogger(Server.class);
    private final String host;
    private final int port;
    private BufferedReader in;
    private BufferedWriter out;
    private BufferedReader inputUser;
    private Socket socket;


    public Client(String host, int port) {
        this.host = host;
        this.port = port;
    }

    @Override
    public void run() {
        try {
            socket = new Socket(this.host, this.port);
            inputUser = new BufferedReader(new InputStreamReader(System.in, StandardCharsets.UTF_8));
            in = new BufferedReader(new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8));
            out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream(), StandardCharsets.UTF_8));
            new ReadMsg().start();
            new WriteMsg().start();
        } catch (IOException e) {
            e.printStackTrace();
            LOG.error("Socket failed",e);
        }
    }

    private void downService() {
        try {
            if (!socket.isClosed()) {
                socket.close();
                in.close();
                out.close();
            }
        } catch (IOException e) {
            LOG.debug("Error, while try to down Service", e);
        }
    }

    /**
     * Thread чтения сообщений с сервера
     */
    private class ReadMsg extends Thread {
        @Override
        public void run() {

            String str;
            try {
                while (true) {
                    str = in.readLine();
                    if (str.equals("stop")) {
                        Client.this.downService();
                        break;
                    }
                    System.out.println(str);
                }
            } catch (IOException e) {
                LOG.debug("Error, while try to down Service in ReadMsg Thread", e);
                Client.this.downService();
            }
        }
    }

    /**
     * Thread, отправляющая сообщения, приходящие с консоли на сервер
     */
    public class WriteMsg extends Thread {

        @Override
        public void run() {
            while (true) {
                String userWord;
                try {
                    userWord = inputUser.readLine();
                    if (userWord.equals("stop")) {
                        out.write("stop" + "\n");
                        Client.this.downService();
                        break;
                    } else {
                        out.write(userWord + "\n");
                    }
                    out.flush();
                } catch (IOException e) {
                    Client.this.downService();
                }
            }
        }
    }
}
