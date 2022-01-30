package com.example.demo.core.processors;

import com.example.demo.core.data.CommandLine;
import com.example.demo.core.data.Response;
import com.example.demo.core.tools.Converter;

public class RequestProcessor {

  private Converter converter;
  private CommandProcessor commandProcessor;

  public RequestProcessor() {
    this.converter = new Converter();
    this.commandProcessor = new CommandProcessor();
  }

  public Response processRequest(String request) {
    CommandLine commandLine = converter.convertToCommandLine(request);
    return this.commandProcessor.processCommand(commandLine);
  }
}
