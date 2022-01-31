package com.example.demo;

import java.io.*;
import java.net.Socket;

public class Client {
    private static final int serverPort = 9090;
    private Socket socket;

    public void start() throws IOException {
        var reader = new BufferedReader(new InputStreamReader(System.in));
        while (true) {
            socket = new Socket("localhost", serverPort);
            var in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            var out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            String command = reader.readLine();
            out.write(command + "\n");
            out.flush();
            String response = in.readLine();
            System.out.println(response);
        }
    }
}