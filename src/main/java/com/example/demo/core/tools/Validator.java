package com.example.demo.core.tools;

import com.example.demo.core.data.CommandLine;
import com.example.demo.core.enums.Result;
import com.example.demo.core.exceptions.ErrorException;

public class Validator {
  public boolean validate(CommandLine commandLine) {
    if (commandLine.getUser() == null
            || commandLine.getUser().isBlank()
            || commandLine.getArg() == null
            || commandLine.getArg().isBlank()) {
      return false;
    }
    return true;
  }
}
