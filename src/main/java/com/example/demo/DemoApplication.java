package com.example.demo;

import java.util.Scanner;

public class DemoApplication {

  public static void main(String[] args) {

    Scanner scanner = new Scanner(System.in);

    while (true) {
      String request = scanner.nextLine();

      if ("q".equals(request)) {
        break;
      }
      System.out.println(split(request));
    }
  }

  public static String split(String request) {
    String response = "WRONG_FORMAT";
    if (Controller.validateFormat(request)) {
      String[] request_parts = request.split(" ");
      String user = request_parts[0];
      String command = request_parts[1];
      String arg = request_parts[2];
      response = start(user, command, arg);
    }
    return response;
  }

  private static String start (String user, String command, String arg){
    String response;
    switch (command) {
      case ("CREATE_TASK"):
        response = Controller.createTask(user, arg);
        break;
      case ("DELETE_TASK"):
        response = Controller.deleteTask(user, arg);
        break;
      case ("CLOSE_TASK"):
        response = Controller.closeTask(user, arg);
        break;
      case ("REOPEN_TASK"):
        response = Controller.reopenTask(user, arg);
        break;
      case ("LIST_TASK"):
        response = "TASKS " + Controller.showTaskList(arg);
        break;
      default:
        response = "ERROR";
    }
    return response;
  }
}