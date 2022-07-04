package com.example.demo.bootstrap;

import com.example.demo.api.controller.ITaskController;
import com.example.demo.api.repository.ITaskRepository;
import com.example.demo.api.service.ITaskService;
import com.example.demo.controller.TaskController;
import com.example.demo.enumerated.Command;
import com.example.demo.enumerated.Result;
import com.example.demo.repository.TaskRepository;
import com.example.demo.service.TaskService;

public class Bootstrap {

    private final ITaskRepository taskRepository = new TaskRepository();
    private final ITaskService taskService = new TaskService(taskRepository);
    private final ITaskController taskController = new TaskController(taskService);

    public String run(final String command) {
        return parseCommand(command);
    }

    public String parseCommand(final String command) {
        if (command == null)
            return Result.WRONG_FORMAT.toString();
        if (Command.__DELETE_ALL.toString().equals(command))
            return taskController.clearTasks();
        final String[] commandArg = command.split(" ");
        if (commandArg.length != 3)
            return Result.WRONG_FORMAT.toString();
        final String user = commandArg[0];
        final String commandName = commandArg[1];
        final String arg = commandArg[2];
        return parseArgs(user, commandName, arg);
    }

    public String parseArgs(final String user, final String command, final String arg) {
        if (user == null || command == null || arg == null)
            return Result.WRONG_FORMAT.toString();
        switch (command) {
            case "CREATE_TASK":
                return taskController.createTask(arg, user);
            case "CLOSE_TASK":
                return taskController.closeTaskByName(arg, user);
            case "REOPEN_TASK":
                return taskController.reopenTaskByName(arg, user);
            case "LIST_TASK":
                return taskController.showTaskListByUser(arg);
            default:
                return Result.WRONG_FORMAT.toString();
        }
    }

}
