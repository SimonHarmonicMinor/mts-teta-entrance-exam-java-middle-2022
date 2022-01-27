package mts.teta.exam;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Server {

  private static final Logger LOG = LoggerFactory.getLogger(Server.class);

  private ServerSocket serverSocket;

  public void start() throws IOException
  {
    start(9090);
  }

  public void start(int port) throws IOException {
    serverSocket = new ServerSocket(port);
    Thread serverThread = new Thread(() -> {

      var commandProcessor=new CommandProcessor();

      while (true) {
        try {
          Socket connection = serverSocket.accept();

          Thread connectionThread = new Thread( () -> {
            try (
                    BufferedReader serverReader = new BufferedReader(
                            new InputStreamReader(connection.getInputStream()));
                    PrintWriter serverWriter = new PrintWriter(connection.getOutputStream(),true)
            ) {
              LOG.debug("New client connected");
              String line;
              while ((line = serverReader.readLine()) != null) {
                LOG.debug("Request captured: " + line);

                try {
                  synchronized (commandProcessor) {
                    var response=commandProcessor.ProcessCommandText(line);
                    serverWriter.println(response);
                    LOG.debug("Response sent: " + response);
                  }
                } catch (Exception e) {
                  LOG.error("Error during request proceeding 1", e);
                  serverWriter.println(ResultType.ERROR.name());
                }
              }
              LOG.debug("Client disconnected");
            }
            catch (Exception e) {
              LOG.error("Error during request proceeding 2", e);
            }
          });

          connectionThread.setDaemon(true);
          connectionThread.start();

        } catch (Exception e) {
          LOG.warn("Accept socket closed, shutting down");
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