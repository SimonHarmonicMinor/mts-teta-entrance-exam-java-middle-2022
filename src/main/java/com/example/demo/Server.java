package com.example.demo;

import com.example.demo.lexer.SwpLexer;
import com.example.demo.lexer.TokenizeException;
import com.example.demo.model.Result;
import com.example.demo.model.SwpCommand;
import com.example.demo.parser.SwpParseException;
import com.example.demo.parser.SwpParser;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.ServerSocket;
import java.net.Socket;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Server {

    private static final Logger LOG = LoggerFactory.getLogger(Server.class);

    private ServerSocket serverSocket;
    private final SwpContext swpContext;

    public Server(SwpContext swpContext) {
        this.swpContext = swpContext;
    }

    public void start() throws IOException {
        serverSocket = new ServerSocket(9090);
        Thread serverThread = new Thread(() -> {
            while (true) {
                try {
                    Socket connection = serverSocket.accept();
                    try (
                            BufferedReader serverReader = new BufferedReader(
                                    new InputStreamReader(connection.getInputStream()));
                            Writer serverWriter = new BufferedWriter(
                                    new OutputStreamWriter(connection.getOutputStream()));
                    ) {
                        String line = serverReader.readLine();
                        LOG.debug("Request captured: " + line);
                        // В реализации по умолчанию в ответе пишется та же строка, которая пришла в запросе
                        serverWriter.write(processLine(swpContext, line));
                        serverWriter.flush();
                    }
                } catch (Exception e) {
                    LOG.error("Error during request proceeding", e);
                    break;
                }
            }
        });
        serverThread.setDaemon(true);
        serverThread.start();
    }

    public void stop() throws Exception {
        serverSocket.close();
    }

    private String processLine(SwpContext context, String line) throws TokenizeException, SwpParseException {
        SwpCommand command = new SwpParser().parse(new SwpLexer(line).tokenize());
        Result result = command.execute(context);
        return result.toString();
    }
}