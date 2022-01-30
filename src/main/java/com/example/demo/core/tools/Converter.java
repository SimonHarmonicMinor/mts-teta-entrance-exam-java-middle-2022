package com.example.demo.core.tools;

import com.example.demo.core.data.CommandLine;
import com.example.demo.core.enums.Command;
import com.example.demo.core.enums.Result;
import com.example.demo.core.exceptions.ErrorException;
import com.example.demo.core.exceptions.WrongFormatException;

public class Converter {

  public CommandLine convertToCommandLine(String request) {

    if (request == null || request.isBlank()) {
      throw new WrongFormatException(Result.WRONG_FORMAT.toString());
    }

    String[] requestSplit = request.split(" ");
    if (requestSplit.length != 2 && requestSplit.length != 3) {
      throw new WrongFormatException(Result.WRONG_FORMAT.toString());
    }

    Command command = null;
    try {
      command = Command.valueOf(requestSplit[1]);
    } catch (IllegalArgumentException illegalArgumentException) {
      throw new ErrorException(Result.ERROR.toString());
    }

    if (requestSplit.length == 2) {
      return new CommandLine(requestSplit[0], command);
    }

    if (requestSplit.length == 3) {
      return new CommandLine(requestSplit[0], command, requestSplit[2]);
    }
    return new CommandLine();
  }
}
