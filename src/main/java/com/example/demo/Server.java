package com.example.demo;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

import com.example.demo.request.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Server {

  private static final Logger logger = LoggerFactory.getLogger(Server.class);

  private ServerSocket serverSocket;
  private Connector connector = new Connector();

  public void start() throws IOException {

    serverSocket = new ServerSocket(9090);

    Thread serverThread = new Thread(() -> {
      while (true) {
        try (
          Socket connection = serverSocket.accept();
          Scanner scanner = new Scanner(
                new InputStreamReader(connection.getInputStream()));
          BufferedWriter serverWriter = new BufferedWriter(
                new OutputStreamWriter(connection.getOutputStream()))) {

            while (scanner.hasNextLine()) {
              String line = scanner.nextLine();
              if(line.equals("exit")) {
                this.stop();
                System.exit(0);
                break;
              }
              logger.info("Request captured: " + line);

              String response = this.getResponse(line);
              logger.info("Response: " + response);

              serverWriter.write(response);
              serverWriter.newLine();
              serverWriter.flush();
            }

        } catch (Exception e) {
          logger.error("Error during request proceeding", e);
          break;
        }
      }
    });

    serverThread.setDaemon(true);
    serverThread.start();
    logger.info("last");
  }

  public void stop() throws Exception {
    serverSocket.close();
  }


  private String getResponse(String request) {

    try {
      RequestObject requestObject = RequestObject.parse(request);
      return connector.pullChange(requestObject);

    } catch (ParseInvalidException e) {
      logger.error(e.getMessage());
      return ResponseBuilder.createResponse(Response.WRONG_FORMAT);
    }

  }


}