package com.example.demo.core.data;

import com.example.demo.core.enums.Result;

public class Response {
  private Result result;
  private String arg;

  public Response(Result result) {
    this.result = result;
  }

  public Response(Result result, String arg) {
    this.result = result;
    this.arg = arg;
  }

  public Result getResult() {
    return result;
  }

  public void setResult(Result result) {
    this.result = result;
  }

  public String getArg() {
    return arg;
  }

  public void setArg(String arg) {
    this.arg = arg;
  }

  @Override
  public String toString() {
    final StringBuffer sb = new StringBuffer();
    sb.append(result);
    if (arg != null && !arg.isBlank()) {
      sb.append(" ").append(arg);
    }
    return sb.toString();
  }
}
