package com.example.demo.exception;

public class DeleteTaskException extends RuntimeException {

  public DeleteTaskException(String name, String status) {
    super(String.format("unable to delete task with name %s; current status: %s, needed status: CLOSED", name, status));
  }
}
