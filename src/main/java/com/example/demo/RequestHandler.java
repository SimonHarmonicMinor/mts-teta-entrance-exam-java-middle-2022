package com.example.demo;

import java.util.ArrayList;
import java.util.Objects;

class RequestHandler {

    private static final ArrayList<Task> taskList = new ArrayList<>();

    static String handleRequest(String request) {
        String[] requestArray = request.split(" ");
        if (!validateRequest(requestArray)) {
            return String.valueOf(TaskResponse.WRONG_FORMAT);
        }

        // request => user - command - arg
        String user = "";
        String command = "";
        String arg = "";


        if (requestArray.length == 1) {
            command = requestArray[0];
        } else {
            user = requestArray[0];
            command = requestArray[1];
            arg = requestArray[2];
        }


        switch (Command.valueOf(command)) {
            case CREATE_TASK: {

                if (isObjectExist(arg)) {
                    return String.valueOf(TaskResponse.ERROR);
                }
                taskList.add(new Task(requestArray));
                return String.valueOf(TaskResponse.CREATED);
            }

            case CLOSE_TASK: {
                if (!isAccessGranted(user, arg)) {
                    return String.valueOf(TaskResponse.ACCESS_DENIED);
                }
                if (getTaskObject(requestArray) != null) {
                    return String.valueOf(Objects.requireNonNull(getTaskObject(requestArray)).closeTask());
                }
                return String.valueOf(TaskResponse.ERROR);
            }

            case DELETE_TASK: {
                if (!isObjectExist(arg)) {
                    return String.valueOf(TaskResponse.ERROR);
                }
                if (!isAccessGranted(user, arg)) {
                    return String.valueOf(TaskResponse.ACCESS_DENIED);
                }
                for (Task task : taskList) {
                    if (task.getUserName().equals(user)
                            && task.getTaskName().equals(arg)
                            && task.getTaskState().equals(String.valueOf(TaskState.CLOSED))) {
                        taskList.remove(task);
                        return String.valueOf(TaskResponse.DELETED);
                    }
                }
                return String.valueOf(TaskResponse.ERROR);
            }


            case REOPEN_TASK: {
                if (!isObjectExist(arg) || !checkState(arg, TaskState.CLOSED)) {
                    return String.valueOf(TaskResponse.ERROR);
                }
                if (!isAccessGranted(user, arg)) {
                    return String.valueOf(TaskResponse.ACCESS_DENIED);
                }
                if (getTaskObject(requestArray) != null) {
                    return String.valueOf(Objects.requireNonNull(getTaskObject(requestArray)).reopenTask());
                }
                return String.valueOf(TaskResponse.ERROR);
            }

            case LIST_TASK: {
                return getTaskList(user);
            }

            case __DELETE_ALL: {
                taskList.clear();
                return "deleted";
            }
        }
        return String.valueOf(TaskResponse.ERROR);
    }


    private static Task getTaskObject(String[] requestArray) {
        for (Task task : taskList) {
            if (task.getUserName().equals(requestArray[0]) && task.getTaskName().equals(requestArray[2])) {
                return task;
            }
        }
        return null;
    }


    private static boolean validateRequest(String[] requestArray) {
        if (requestArray.length == 1) {
            return requestArray[0].equals("__DELETE_ALL");
        }

        if (requestArray.length == 2) {
            try {
                Command.valueOf(requestArray[0]);
            } catch (IllegalArgumentException e) {
                return false;
            }
            return true;
        }


        if (requestArray.length == 3) {
            try {
                Command.valueOf(requestArray[1]);
            } catch (IllegalArgumentException e) {
                return false;
            }

            return true;
        }
        return false;
    }

    private static boolean isAccessGranted(String userName, String taskName) {
        for (Task task : taskList) {
            if (task.getUserName().equals(userName) && task.getTaskName().equals(taskName)) {
                return true;
            }
        }
        return false;
    }


    private static boolean isObjectExist(String taskName) {

        for (Task task : taskList) {
            if (task.getTaskName().equals(taskName)) {
                return true;
            }
        }
        return false;
    }

    private static boolean checkState(String taskName, TaskState taskState) {
        for (Task task : taskList) {
            if (task.getTaskName().equals(taskName) &&
                    task.getTaskState().equals(taskState.toString())) {
                return true;
            }
        }
        return false;
    }

    private static String getTaskList(String userName) {
        StringBuilder result = new StringBuilder();
        for (Task task : taskList) {
            if (task.getUserName().equals(userName)) {
                result.append(task.getTaskName()).append(", ");
            }
        }
        if (result.length() > 1) {
            result.deleteCharAt(result.length() - 1).deleteCharAt(result.length() - 1);
        }
        return "TASKS [" + result.toString() + "]";
    }

}
