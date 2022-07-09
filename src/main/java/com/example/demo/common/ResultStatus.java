package com.example.demo.common;

public enum ResultStatus {

  CREATED("CREATED"),
  DELETED("DELETED"),
  CLOSED("CLOSED"),
  REOPENED("REOPENED"),
  TASKS("TASKS"),
  WRONG_FORMAT("WRONG_FORMAT"),
  ACCESS_DENIED("ACCESS_DENIED"),
  ERROR("ERROR");

  private final String key;

  ResultStatus(String key) {
    this.key = key;
  }

  public String getKey() {
    return key;
  }
}
