package logic;

import objects.Request;
import assets.States;
import objects.Task;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TaskActions {

    public static final HashMap<String, Task> tasks = new HashMap<>();
    public static final HashMap<String, Task> deletedTasks = new HashMap<>();

    public static boolean isCommandFormatCorrect(String command) {
        return command.matches("^[a-zA-Z]+\\s[a-zA-Z_]+\\s\\w+$");
    }

    public static boolean userIsNotAllowedToChangeTask(String userName, String taskName) {
        String currentTaskUserName = tasks.get(taskName).getUserName();
        return !currentTaskUserName.equals(userName);
    }

    public static boolean isTaskNameUnique(String taskName) {
        return !tasks.containsKey(taskName);
    }

    public String createTask(Request request) {
        Task task = new Task(request);
        if (isTaskNameUnique(request.getArg())) {
            task.setState(States.CREATED);
            tasks.put(task.getTaskName(), task);
            return "CREATED";
        } else return "ERROR";
    }

    public String closeTask(Request request) {
        if (tasks.containsKey(request.getArg())) {
            if (userIsNotAllowedToChangeTask(request.getUserName(), request.getArg())) return "ACCESS_DENIED";
            tasks.get(request.getArg()).setState(States.CLOSED);
            return "CLOSED";
        } else return "ERROR";
    }

    public String deleteTask(Request request) {

        if (tasks.containsKey(request.getArg())) {
            if (userIsNotAllowedToChangeTask(request.getUserName(), request.getArg())) return "ACCESS_DENIED";
            Task task = tasks.get(request.getArg());
            if (task.getState().equals(States.CREATED)) return "ERROR";
            task.setState(States.DELETED);
            deletedTasks.put(task.getTaskName(), task);
            tasks.remove(task.getTaskName());
            return "DELETED";
        } else return "ERROR";
    }

    public String reopenTask(Request request) {
        if (tasks.containsKey(request.getArg())) {
            if (userIsNotAllowedToChangeTask(request.getUserName(), request.getArg())) return "ACCESS_DENIED";
            tasks.get(request.getArg()).setState(States.CREATED);
            return "REOPENED";
        } else return "ERROR";
    }

    public String getUserTaskList(Request request) {
        List<String> selectedTasks = new ArrayList<>();
        for (Map.Entry<String, Task> entry : tasks.entrySet()) {
            String taskUser = entry.getValue().getUserName();
            if (taskUser.equals(request.getArg())) selectedTasks.add(entry.getValue().getTaskName());
        }
        return "TASKS " + selectedTasks;
    }
}
