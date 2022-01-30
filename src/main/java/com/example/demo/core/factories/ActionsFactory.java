package com.example.demo.core.factories;

import com.example.demo.core.enums.Command;
import com.example.demo.core.enums.Result;
import com.example.demo.core.exceptions.ErrorException;

public class ActionsFactory {

  public Action getAction(Command command) {
    Action action = null;
    switch (command) {
      case CREATE_TASK:
        action = new CreateTaskAction();
        break;
      case CLOSE_TASK:
        action = new CloseTaskAction();
        break;
      case REOPEN_TASK:
        action = new ReopenTaskAction();
        break;
      case DELETE_TASK:
        action = new DeleteTaskAction();
        break;
      case LIST_TASK:
        action = new ListTaskAction();
        break;
      default:
        throw new ErrorException(Result.ERROR.toString());
    }
    return action;
  }
}
