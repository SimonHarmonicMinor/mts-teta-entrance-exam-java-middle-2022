package mts.teta.exam;

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

  public void start() throws IOException {
    serverSocket = new ServerSocket(9090);
    Thread serverThread = new Thread(() -> {

      var commandProcessor=new CommandProcessor();

      while (true) {
        try {
          Socket connection = serverSocket.accept();

          Thread connectionThread = new Thread( () -> {
            try (
                    BufferedReader serverReader = new BufferedReader(
                            new InputStreamReader(connection.getInputStream()));
                    Writer serverWriter = new BufferedWriter(
                            new OutputStreamWriter(connection.getOutputStream()))
            ) {
              LOG.debug("New client connected");
              String line;
              while ((line = serverReader.readLine()) != null) {
                LOG.debug("Request captured: " + line);

                try {
                  synchronized (commandProcessor) {
                    var response=commandProcessor.ProcessCommandText(line);
                    serverWriter.write(response+"\r\n");
                    serverWriter.flush();
                    LOG.debug("Response sent: " + response);
                  }
                } catch (Exception e) {
                  LOG.error("Error during request proceeding 1", e);
                  serverWriter.write(ResultType.ERROR.name());
                  serverWriter.flush();
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
          //LOG.error("Error during request proceeding 3", e);
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