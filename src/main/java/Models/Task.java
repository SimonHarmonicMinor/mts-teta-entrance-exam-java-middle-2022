package Models;

import java.util.Map;

public class Task {
    public static Commands commands = new Commands();
    public String name;
    public States state;
    public User user;
    Map<String, Task> tasks;

    public enum States {
        CREATED,
        CLOSED,
        DELETED;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public States getState() {
        return state;
    }

    public void setState(States state) {
        this.state = state;
    }

    public Task(User user, String name) {
        state = States.CREATED;
        this.name = name;
        this.user = user;
    }

    private static Task getTask(Map<String, Task> tasks, String user, String name) throws Exception {
        Task task = tasks.get(name);
        if (task == null)
            throw new Exception(commands.getError());
        if (!user.equals(task.user.getName()))
            throw new Exception(commands.getAccessDenied());
        return task;
    }

    public static void createTask(Map<String, Task> tasks, User user, String name) throws Exception {
        if (tasks.containsKey(name))
            throw new Exception(commands.getError());
        Task task = new Task(user, name);
        tasks.put(name, task);
        user.addTask(name);
    }

    public static void closeTask(Map<String, Task> tasks, String user, String name) throws Exception {
        Task task = getTask(tasks, user, name);
        if (!task.getState().equals(States.CREATED))
            throw new Exception(commands.getError());
        task.setState(States.CLOSED);
    }

    public static void deleteTask(Map<String, Task> tasks, String user, String name) throws Exception {
        Task task = getTask(tasks, user, name);
        if (!task.getState().equals(States.CLOSED))
            throw new Exception(commands.getError());
        else task.setState(States.DELETED);
    }

    public static void reopenTask(Map<String, Task> tasks, String user, String name) throws Exception {
        Task task = getTask(tasks, user, name);
        if (!task.getState().equals(States.CLOSED))
            throw new Exception(commands.getError());
        else task.setState(States.CREATED);
    }


    public static void getListTasks(Map<String, User> users, String user) throws Exception {
        Commands commands = new Commands();
        User userForTasks = users.get(user);
        if (userForTasks.getName() == null)
            throw new Exception(commands.getError());
        else {
            System.out.println(commands.getTasks() + " [" + String.join(", ", userForTasks.getTasks()) + "]");
        }
    }
}
