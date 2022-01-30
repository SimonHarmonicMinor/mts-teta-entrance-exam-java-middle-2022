package mts.teta.entrance.exam;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Server {

  private static final Logger LOG = LoggerFactory.getLogger(Server.class);

  private ServerSocket serverSocket;

  public void start() throws IOException {
    serverSocket = new ServerSocket(9090);
    Thread serverThread =
        new Thread(
            () -> {
              while (true) {
                try {
                  Socket connection = serverSocket.accept();
                  try (BufferedReader serverReader =
                          new BufferedReader(
                              new InputStreamReader(connection.getInputStream(), "UTF-8"));
                      Writer serverWriter =
                          new BufferedWriter(
                              new OutputStreamWriter(connection.getOutputStream(), "UTF-8")); ) {
                    String line = serverReader.readLine();
                    LOG.info("Request captured: " + line);
                    Request request;
                    try {
                      request = new Request(line);
                    } catch (SecurityException sept) {
                      LOG.info("Request has wrong format");
                      serverWriter.write("WRONG_FORMAT");
                      serverWriter.flush();
                      continue;
                    }
                    String answer = request.process();
                    serverWriter.write(answer);
                    serverWriter.flush();
                  }
                } catch (SocketException se) {
                  LOG.info("Socket is closed (most probably by testing code)");
                  break;
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

  public static void main(String[] args) {

    Server s = new Server();
    try {
      s.start();
    } catch (IOException e) {
      LOG.error("Couldnt start the Server");
    }
  }
}
