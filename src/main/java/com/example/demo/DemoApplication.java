package com.example.demo;

public class DemoApplication {
  public static TaskState errorState = TaskState.ERROR;
  public static AllTasks tasks = new AllTasks();


  public static void main(String[] args) throws Exception {

  }

  public static String result(String userCommand, AllTasks tasks){
    String rez = "";
    String user = "";
    String command = "";
    String arg = "";

    try {
      user =     userCommand.split(" ")[0];
      command =  userCommand.split(" ")[1];
      arg =      userCommand.split(" ")[2];

    }
    catch (Exception e) {
      rez = TaskState.WRONG_FORMAT.name();
    }

    if (rez != TaskState.WRONG_FORMAT.name()) {
      AllTasks.setActiveUser(user);
      switch (command) {
        case "CREATE_TASK":
          rez = tasks.CREATE(arg).name();
          break;
        case "CLOSE_TASK":
          rez = tasks.ACTION(arg, UserAction.CLOSE).name();
          break;
        case "DELETE_TASK":
          rez = tasks.ACTION(arg, UserAction.DELETE).name();
          break;
        case "REOPEN_TASK":
          rez = tasks.ACTION(arg, UserAction.REOPEN).name();
          break;
        case "LIST_TASK":
          rez = "TASKS " + tasks.LIST(arg);
          break;
        default:
          rez = TaskState.WRONG_FORMAT.name();
      }
    }
    return rez;
  }
}
