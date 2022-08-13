package com.example.service;

import java.util.ArrayList;
import java.util.List;

import static com.example.data.Data.allTasks;
import static com.example.data.Data.allUsers;

public class CommandService {
    private final TaskService taskService = new TaskService();
    private final UserService userService = new UserService();
    private final List<String> allCommands = new ArrayList<>(List.of("CREATE_TASK", "CLOSE_TASK", "DELETE_TASK", "REOPEN_TASK", "LIST_TASK", "__DELETE_ALL"));

    private static final int USER_INDEX = 0;
    private static final int CMD_INDEX = 1;
    private static final int TASK_INDEX = 2;

    public String executeMainLogic(String incomeString) {
        String resultString;

        String[] strings = incomeString.split(" ");
        int stringsLength;
        try {
            stringsLength = strings.length;
            if (stringsLength == 1 && strings[0].equals("__DELETE_ALL")) {
                allUsers.clear();
                allTasks.clear();
                resultString = "";
            } else if (stringsLength == 3 && allCommands.contains(strings[1])) {
                String userNameFromArgs = strings[USER_INDEX];
                String cmdFromArgs = strings[CMD_INDEX];
                String argFromArgs = strings[TASK_INDEX];
                if (!userService.checkUserAlreadyCreated(userNameFromArgs)) {
                    userService.createNewUser(userNameFromArgs);
                }
                switch (cmdFromArgs) {
                    case "CREATE_TASK":
                        resultString = taskService.createNewTask(userNameFromArgs, argFromArgs);
                        break;
                    case "CLOSE_TASK":
                        resultString = taskService.checkAndChangeStatus(userNameFromArgs, argFromArgs, "CLOSE_TASK");
                        break;
                    case "DELETE_TASK":
                        resultString = taskService.checkAndChangeStatus(userNameFromArgs, argFromArgs, "DELETE_TASK");
                        taskService.deleteTaskEverywhere(userNameFromArgs, argFromArgs);
                        break;
                    case "REOPEN_TASK":
                        resultString = taskService.checkAndChangeStatus(userNameFromArgs, argFromArgs, "REOPEN_TASK");
                        break;
                    case "LIST_TASK":
                        resultString = taskService.printAllTaskCurrentUser(argFromArgs);
                        break;
                    default:
                        resultString = "ERROR";
                        break;
                }
            } else {
                resultString = "WRONG_FORMAT";
            }
        } catch (RuntimeException e) {
            resultString = "ERROR";
        }
        return resultString;
    }


}