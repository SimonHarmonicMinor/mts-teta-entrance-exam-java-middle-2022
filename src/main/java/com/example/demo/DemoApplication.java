package com.example.demo;


import controllers.AppController;
import controllers.VerificationController;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class DemoApplication {

  public static void main(String[] args) throws IOException {

    Scanner scanner = new Scanner(System.in);
    String user = "";
    String command = "";
    String arg = "";
    String startFlag= "";
    AppController appController = new AppController();
    VerificationController verificationController = new VerificationController();
    List<String> reqArr;


    while (true) {
      System.out.println("Введите ваш запрос в формате USER COMMAND ARG:");
      String request = scanner.nextLine();

      //Проверяем корректность формата
      startFlag = verificationController.validateFormat(request) ? "Y" : "N";



      if ("Y".equals(startFlag)) {
        //Если прошли проверку, раскладывем строку в коллекцию и приступаем к дальнейшей обработке
        reqArr = Arrays.asList(request.split(" "));
        user = reqArr.get(0);
        command = reqArr.get(1);
        arg = reqArr.get(2);

        //Выполняем операцию и воводим на экран в соответствии с результатом
        System.out.println(doAnyOperation(user, command, arg, appController));
      } else {
        System.out.println("WRONG_FORMAT");
      }

    }
  }

  private static String doAnyOperation(String user, String command, String arg, AppController appController) {
    String response = "";
    switch (command) {
      case ("CREATE_TASK"):
        response = appController.createTask(user, arg);
        break;
      case ("DELETE_TASK"):
        response = appController.deleteTask(user, arg);
        break;
      case ("CLOSE_TASK"):
        response = appController.closeTask(user, arg);
        break;
      case ("REOPEN_TASK"):
        response = appController.reopenTask(user, arg);
        break;
      case ("LIST_TASK"):
        response = "TASKS " + appController.showTasksList(arg).toString();
        break;
      default:
        response =  "ERROR";
    }

    return response;
  }





}
