package com.example.demo;

import com.example.demo.exceptions.AccessDeniedException;
import com.example.demo.exceptions.AnyOtherErrorException;
import com.example.demo.exceptions.WrongFormatException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

import static com.example.demo.enums.ServerResponse.*;
import static com.example.demo.service.CommandProcessingService.getResponse;

public class Server {

  private static final Logger LOG = LoggerFactory.getLogger(Server.class);

  private ServerSocket serverSocket;

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
                  new OutputStreamWriter(connection.getOutputStream()))
          ) {
            String line = serverReader.readLine();
            LOG.debug("Request captured: {}", line);

            String response;
            try {
              response = getResponse(line);
            } catch (AccessDeniedException e) {
              response = ACCESS_DENIED.name();
              LOG.error(e.getMessage());
            } catch (AnyOtherErrorException e) {
              response = ERROR.name();
              LOG.error(e.getMessage());
            } catch (WrongFormatException e) {
              response = WRONG_FORMAT.name();
              LOG.error(e.getMessage());
            }

            serverWriter.write(response);
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
}