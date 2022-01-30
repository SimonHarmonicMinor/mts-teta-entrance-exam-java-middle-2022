package com.example.demo;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;

import com.example.demo.exception.ServiceException;
import com.example.demo.model.BaseCommandResult;
import com.example.demo.model.Command;
import com.example.demo.service.CommandExecutor;
import com.example.demo.service.CommandReader;
import com.example.demo.service.CommandRequestHandler;
import com.example.demo.service.impl.CommandExecutorImpl;
import com.example.demo.service.impl.CommandReaderImpl;
import com.example.demo.service.impl.CommandRequestHandlerImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Server {

  private static final Logger LOG = LoggerFactory.getLogger(Server.class);

  private ServerSocket serverSocket;
  private CommandExecutor executor = new CommandExecutorImpl();
  private CommandReader reader = new CommandReaderImpl();
  private CommandRequestHandler handler = new CommandRequestHandlerImpl();

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
            BaseCommandResult result = null;
            try {
              result = executor.execute(handler.handle(reader.readFromString(line)));
            } catch (ServiceException e) {
              switch (e.getType()) {
                case WRONG_FORMAT:
                  result = new BaseCommandResult(BaseCommandResult.Status.WRONG_FORMAT);
                  break;
                case ACCESS_DENIED:
                  result = new BaseCommandResult(BaseCommandResult.Status.ACCESS_DENIED);
                  break;
                case COMMON:
                  result = new BaseCommandResult(BaseCommandResult.Status.ERROR);
                  break;
              }
            }
            if (result == null) {
              result = new BaseCommandResult(BaseCommandResult.Status.ERROR);
            }
            serverWriter.write(result.toString());
            serverWriter.flush();
          }
        } catch (SocketException se) {
          if ("Socket closed".equals(se.getMessage())) {
            LOG.info("Server stopped");
            break;
          }
          LOG.error("Got error with server socket", se);
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
}