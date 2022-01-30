package com.example.demo.core.data;

import com.example.demo.core.enums.Command;

public class CommandLine {
  private String user;
  private Command command;
  private String arg;

  public CommandLine() {}

  public CommandLine(String user, Command command) {
    this.user = user;
    this.command = command;
  }

  public CommandLine(String user, Command command, String arg) {
    this.user = user;
    this.command = command;
    this.arg = arg;
  }

  public String getUser() {
    return user;
  }

  public void setUser(String user) {
    this.user = user;
  }

  public Command getCommand() {
    return command;
  }

  public void setCommand(Command command) {
    this.command = command;
  }

  public String getArg() {
    return arg;
  }

  public void setArg(String arg) {
    this.arg = arg;
  }

  @Override
  public String toString() {
    final StringBuffer sb = new StringBuffer("CommandLine{");
    sb.append("user='").append(user).append('\'');
    sb.append(", command=").append(command);
    sb.append(", arg='").append(arg).append('\'');
    sb.append('}');
    return sb.toString();
  }
}
