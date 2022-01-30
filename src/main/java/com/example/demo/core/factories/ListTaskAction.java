package com.example.demo.core.factories;

import com.example.demo.core.data.CommandLine;
import com.example.demo.core.data.Response;
import com.example.demo.core.data.Task;
import com.example.demo.core.enums.Result;
import com.example.demo.core.exceptions.AccessDeniedException;

import java.util.stream.Collectors;

public class ListTaskAction extends Action {
  @Override
  public Response execute(CommandLine commandLine) {
    String tasks =
        "["
            + tasksRepository.findByOwner(commandLine.getArg()).stream()
                .map(Task::toString)
                .collect(Collectors.joining(", "))
            + "]";
    return new Response(Result.TASKS, tasks);
  }
}
