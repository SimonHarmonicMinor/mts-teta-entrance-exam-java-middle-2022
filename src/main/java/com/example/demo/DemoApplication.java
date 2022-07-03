package com.example.demo;


import java.io.IOException;

public class DemoApplication {

  public static void main(String[] args) throws IOException {
    Server s1 = new Server();
    s1.start();

    Result res = Command.proc_command("VASYA CREATE_TASK CleanRoom");
    System.out.println("RESULT" + ' ' + res.toStr() );

    res = Command.proc_command("PETYA DELETE_TASK CleanRoom");
    System.out.println("RESULT" + ' ' + res.toStr() );

    res = Command.proc_command("PETYA CREATE_TASK Task1");
    System.out.println("RESULT" + ' ' + res.toStr() );

    res = Command.proc_command("PETYA CREATE_TASK Task2");
    System.out.println("RESULT" + ' ' + res.toStr() );

    res = Command.proc_command("VASYA LIST_TASK PETYA");
    System.out.println("RESULT" + ' ' + res.toStr() );

    res = Command.proc_command("VASYA CREATE_TASK CleanRoom");
    System.out.println("RESULT" + ' ' + res.toStr() );
  }
}
