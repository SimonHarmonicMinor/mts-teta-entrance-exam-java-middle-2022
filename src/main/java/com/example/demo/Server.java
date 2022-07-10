package com.example.demo;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Server {

  private static final Logger LOG = LoggerFactory.getLogger(Server.class);

  private ServerSocket serverSocket;
  //initialize tasks map
  private static Map<String, Task> tasks = new HashMap<>();

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
//            serverWriter.write(line);
            serverWriter.write(runCmd(line));
            serverWriter.flush();
          }
        } catch (Exception e) {
          LOG.error("Error during request proceeding", e);
          break;
        }
      }
    });
    serverThread.start();
  }

  public void stop() throws Exception {
    serverSocket.close();
  }

  public String Test(String s) {
    return runCmd(s);
  }

  private String runCmd(String cmd) {
    try {
      if (cmd == null || cmd.trim().length() == 0) return "WRONG_FORMAT";

      String[] arr = cmd.split(" ");

      if (arr.length == 1 && arr[0].equals("__DELETE_ALL")) {
        tasks.clear();
        return "O_o";
      } else if (arr.length == 3) {
        switch (arr[1]) {
          case "CREATE_TASK":
            if (tasks.containsKey(arr[2])) return "ERROR";
            tasks.put(arr[2], new Task("CREATED", arr[0], arr[2]));
            return "CREATED";
          case "CLOSE_TASK":
            if (!tasks.containsKey(arr[2])) return "ERROR";
            if (!tasks.get(arr[2]).getCreatedBy().equals(arr[0])) return "ACCESS_DENIED";
            else {
              if (tasks.get(arr[2]).getStatus().equals("CREATED")) {
                tasks.get(arr[2]).setStatus("CLOSED");
                return "CLOSED";
              } else return "ERROR";
            }
          case "DELETE_TASK":
            if (!tasks.containsKey(arr[2])) return "ERROR";
            if (!tasks.get(arr[2]).getCreatedBy().equals(arr[0])) return "ACCESS_DENIED";
            else {
              if (tasks.get(arr[2]).getStatus().equals("CLOSED")) {
                tasks.remove(arr[2]);
                return "DELETED";
              } else return "ERROR";
            }
          case "REOPEN_TASK":
            if (!tasks.containsKey(arr[2])) return "ERROR";
            if (!tasks.get(arr[2]).getCreatedBy().equals(arr[0])) return "ACCESS_DENIED";
            else {
              if (tasks.get(arr[2]).getStatus().equals("CLOSED")) {
                tasks.get(arr[2]).setStatus("CREATED");
                return "REOPENED";
              } else return "ERROR";
            }
          case "LIST_TASK":
            List<Task> tasklist = new ArrayList<>();
            Iterator i = tasks.entrySet().iterator();
            while (i.hasNext()) {
              Map.Entry pair = (Map.Entry) i.next();
              //сюда можно было бы добавить проверку на статус задачи (в статусе CREATED и CLOSED),
              // но так как других статусов у нас нет - игнорим проверку
              if (((Task) pair.getValue()).getCreatedBy().equals(arr[2])) tasklist.add((Task) pair.getValue());
            }
            //Задачи перечислены в порядке их создания - сортируем по дате создания
//            Collections.sort(tasklist, new Comparator<Task>() {
//              @Override
//              public int compare(Task t1, Task t2) {
//                return t1 == t2 ? 0 : (t1.getCreated().getTime() - t2.getCreated().getTime() < 0 ? -1 : 1);
//              }
//            });
            //по дате сортировать не получается, так как могут быть два таска с одним временем создания (да, до милисекунд),
            //поэтому пришлось вводить номер задачи
            Collections.sort(tasklist, new Comparator<Task>() {
              @Override
              public int compare(Task t1, Task t2) {
                return t1.getTask_num() - t2.getTask_num();
              }
            });
            return "TASKS " + tasklist.toString();
          default:
            return "WRONG_FORMAT";
        }
      } else return "WRONG_FORMAT";
    }
    catch(Exception e){
//      System.out.println(e.toString());
      return "ERROR";
    }
  }
}