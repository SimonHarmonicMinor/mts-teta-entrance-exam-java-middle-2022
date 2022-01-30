package com.example.demo.core.factories;

import com.example.demo.core.data.CommandLine;
import com.example.demo.core.data.Response;
import com.example.demo.core.data.Task;
import com.example.demo.core.data.TasksRepository;
import com.example.demo.core.enums.Result;
import com.example.demo.core.enums.Status;
import com.example.demo.core.exceptions.ErrorException;

public class CreateTaskAction extends Action {

  @Override
  public Response execute(CommandLine commandLine) {

    if (!validator.validate(commandLine)) {
      throw new ErrorException(Result.WRONG_FORMAT.toString());
    }

    if (tasksRepository.isExist(commandLine.getArg())) {
      throw new ErrorException(Result.ERROR.toString());
    }

    Task task = new Task(commandLine.getArg(), Status.CREATED, commandLine.getUser());
    tasksRepository.add(task);
    return new Response(Result.CREATED);
  }
}
