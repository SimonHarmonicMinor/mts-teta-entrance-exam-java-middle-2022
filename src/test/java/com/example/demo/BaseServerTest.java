package com.example.demo;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.slf4j.Logger;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;

import static com.example.demo.Configure.buildPort;
import static org.slf4j.LoggerFactory.getLogger;

public class BaseServerTest {

    private static Socket clientSocket;
    private static PrintWriter out;
    private static BufferedReader in;
    private static Server server;
    private static int port;

    private static final Logger LOG = getLogger(BaseServerTest.class);

    @BeforeAll
    static void beforeAll() throws Exception {
        server = new Server();
        server.start();
        port = buildPort();
    }

    @AfterAll
    static void afterAll() {
        server.stop();
    }

    void beforeSendMessage() throws Exception {
        clientSocket = new Socket(InetAddress.getLocalHost().getHostAddress(), port);
        out = new PrintWriter(clientSocket.getOutputStream(), true);
        in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
    }

    void afterSendMessage() throws Exception {
        in.close();
        out.close();
        clientSocket.close();
    }

    protected String sendMessage(String msg) {
        try {
            beforeSendMessage();

            LOG.debug("sendMessage \"" + msg + "\" ...");
            out.println(msg);

            return in.readLine();
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            try {
                afterSendMessage();
            } catch (Exception e) {
                LOG.error(e.getMessage(), e);
            }
        }
    }
}
