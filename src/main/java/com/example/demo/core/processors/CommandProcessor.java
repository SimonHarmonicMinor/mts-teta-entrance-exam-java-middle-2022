package com.example.demo.core.processors;

import com.example.demo.core.data.CommandLine;
import com.example.demo.core.data.Response;
import com.example.demo.core.factories.Action;
import com.example.demo.core.factories.ActionsFactory;

public class CommandProcessor {

  public Response processCommand(CommandLine commandLine) {
    Action action = new ActionsFactory().getAction(commandLine.getCommand());
    return action.execute(commandLine);
  }
}
