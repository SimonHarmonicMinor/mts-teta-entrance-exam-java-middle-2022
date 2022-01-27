package com.example.demo;

import static org.junit.jupiter.api.Assertions.assertEquals;

import controllers.AppController;
import controllers.VerificationController;
import model.Task;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

class ServerTest { //extends AbstractServerTest {

//  @Test
//  void test() {
//    String response = sendMessage("request");
//    assertEquals("request", response);
//  }
//
//  @Test
//  void test2() {
//    String response = sendMessage("request2");
//    assertEquals("request2", response);
//  }

  @Test
  void applicationTest() {
    String user;
    String command;
    String arg;
    String startFlag;
    String response;
    AppController appController = new AppController();
    VerificationController verificationController = new VerificationController();
    List<String> reqArr;

    List<String> requestList = new ArrayList<>();

    //Примеры из тестового задания
    requestList.add("VASYA CREATE_TASK CleanRoom");  //Ожидаем CREATED
    requestList.add("PETYA DELETE_TASK CleanRoom");  //Ожидаем ACCESS_DENIED
    requestList.add("PETYA CREATE_TASK Task1");      //Ожидаем CREATED
    requestList.add("PETYA CREATE_TASK Task2");      //Ожидаем CREATED
    requestList.add("VASYA LIST_TASK PETYA");        //Ожидаем TASKS [Task1, Task2]
    requestList.add("VASYA CREATE_TASK CleanRoom");  //Ожидаем ERROR


    //Мои примеры (Создание, закрытие, открытия, удаление)
    requestList.add("VASYA CREATE_TASK TestTask2");  //Ожидаем CREATED
    requestList.add("VASYA CREATE_TASK TestTask3");  //Ожидаем CREATED
    requestList.add("VASYA DELETE_TASK TestTask3");  //Ожидаем ERROR (т.к задача в статусе CREATED)
    requestList.add("VASYA CLOSE_TASK TestTask3");   //Ожидаем CLOSED
    requestList.add("VASYA DELETE_TASK TestTask3");  //Ожидаем DELETED
    requestList.add("VASYA CLOSE_TASK TestTask2");  //Ожидаем CLOSED
    requestList.add("VASYA REOPEN_TASK TestTask2");  //Ожидаем REOPENED

    //Примеры на формат
    requestList.add("Vasya CREATE_TASK TestTask4");  //Ожидаем WRONG_FORMAT
    requestList.add("VASYA REMOVE_TASK TestTask4");  //Ожидаем WRONG_FORMAT
    requestList.add("VASYA CREATE_TASK");            //Ожидаем WRONG_FORMAT
    requestList.add("VASYA LIST_TASK Petya");        //Ожидаем WRONG_FORMAT


    for (String str : requestList) {
      startFlag = verificationController.validateFormat(str) ? "Y" : "N";
      if ("Y".equals(startFlag)) {
        //Если прошли проверку, раскладывем строку в коллекцию и приступаем к дальнейшей обработке
        reqArr = Arrays.asList(str.split(" "));
        user = reqArr.get(0);
        command = reqArr.get(1);
        arg = reqArr.get(2);
        //Выполняем операцию и воводим на экран в соответствии с результатом

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
            response = "ERROR";
        }
        System.out.println(str);
        System.out.println(response + "\n");
      } else {
        System.out.println(str);
        System.out.println("WRONG_FORMAT" + "\n");
      }
    }
  }

  @Test
  void formatTest() {
    VerificationController verificationController = new VerificationController();
    String request;
    boolean response;

    request = "Vasya CREATE_TASK FirstTask";
    response = verificationController.validateFormat(request);
    System.out.println("Input string: " + request);
    System.out.println("Result: " + response + "\n");

    request = "VASYA CREATE_TASK FirstTask";
    response = verificationController.validateFormat(request);
    System.out.println("Input string: " + request);
    System.out.println("Result: " + response + "\n");

    request = "VASYA CREATED_TASK FirstTask";
    response = verificationController.validateFormat(request);
    System.out.println("Input string: " + request);
    System.out.println("Result: " + response + "\n");

    request = "VASYA CREATE_TASK FirstTask";
    response = verificationController.validateFormat(request);
    System.out.println("Input string: " + request);
    System.out.println("Result: " + response + "\n");

    request = "VASYA LIST_TASK FirstTask";
    response = verificationController.validateFormat(request);
    System.out.println("Input string: " + request);
    System.out.println("Result: " + response + "\n");

    request = "VASYA LIST_TASK PETYA";
    response = verificationController.validateFormat(request);
    System.out.println("Input string: " + request);
    System.out.println("Result: " + response + "\n");

  }

  @Test
  void uniqTest() {
     ArrayList<Task> tasksCollection = new ArrayList<>();
     VerificationController verificationController = new VerificationController();

     //Проверяем уникальность при статусе одной из записи "DELETED"
     tasksCollection.add(new Task("FiveTask","DELETED", "PETR"));
     tasksCollection.add(new Task("TwoTask","CREATED", "VASYA"));

     System.out.println("Ожидаемый результат: true - запись уникальна");
     System.out.println("Результат: " + verificationController.validateUniqAndNonDeleted("FiveTask", tasksCollection) + "\n");

    //Проверяем уникальность при статусе обеих записей 'CREATED"
    tasksCollection.clear();
    tasksCollection.add(new Task("TwoTask","CREATED", "PETR"));
    tasksCollection.add(new Task("FiveTask","CREATED", "VASYA"));

    System.out.println("Ожидаемый результат: false - не уникальная запись");
    System.out.println("Результат: " + verificationController.validateUniqAndNonDeleted("FiveTask", tasksCollection) + "\n");

    //Проверяем уникальность
    tasksCollection.clear();
    tasksCollection.add(new Task("FirstTask","CREATED", "PETR"));
    tasksCollection.add(new Task("SixTask","CREATED", "VASYA"));

    System.out.println("Ожидаемый результат: true - запись уникальна");
    System.out.println("Результат: " + verificationController.validateUniqAndNonDeleted("FiveTask", tasksCollection) + "\n");


  }
  @Test
  void testAccess() {
    ArrayList<Task> tasksCollection = new ArrayList<>();
    VerificationController verificationController = new VerificationController();

    //Проверяем доступ к удалению, если обращается создатель задачи, но статус задачи "DELETED"
    tasksCollection.add(new Task("FiveTask","DELETED", "PETR"));

    System.out.println("Ожидаемый результат: false - запись удалена");
    System.out.println("Результат: " + verificationController.validateAccess("FiveTask","PETR", tasksCollection) + "\n");

    //Проверяем доступ к удалению, если обращается создатель задачи, и статус задачи "CLOSED"
    tasksCollection.clear();
    tasksCollection.add(new Task("FiveTask","CLOSED", "PETR"));

    System.out.println("Ожидаемый результат: true - запись в статусе CREATED");
    System.out.println("Результат: " + verificationController.validateAccess("FiveTask","PETR", tasksCollection) + "\n");

    //Проверяем доступ к удалению, если обращается не создатель задачи
    tasksCollection.clear();
    tasksCollection.add(new Task("FiveTask","CLOSED", "PETR"));

    System.out.println("Ожидаемый результат: false - запись создал другой юзер");
    System.out.println("Результат: " + verificationController.validateAccess("FiveTask","VASYA", tasksCollection) + "\n");
  }

}