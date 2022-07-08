package com.example.demo.model;

import com.example.demo.common.TaskStatus;

public class TaskModel {

  private int index;
  private final String name;
  private final String owner;
  private TaskStatus status;
  private boolean isDeleted;

  public TaskModel(String name, String owner) {
    this.name = name;
    this.owner = owner;
    this.status = TaskStatus.CREATED;
    this.isDeleted = false;
  }

  public int getIndex() {
    return index;
  }

  public void setIndex(int index) {
    this.index = index;
  }

  public String getName() {
    return name;
  }

  public String getOwner() {
    return owner;
  }

  public TaskStatus getStatus() {
    return status;
  }

  public void setStatus(TaskStatus status) {
    this.status = status;
  }

  public boolean isDeleted() {
    return !isDeleted;
  }

  public void setDeleted(boolean deleted) {
    isDeleted = deleted;
  }
}
