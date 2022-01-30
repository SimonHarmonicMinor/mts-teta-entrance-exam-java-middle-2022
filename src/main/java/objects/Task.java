package objects;

import assets.States;

public class Task {
    String taskName;
    States state;
    String userName;

    public Task(String taskName, States state, String userName) {
        this.taskName = taskName;
        this.state = state;
        this.userName = userName;
    }

    public Task(Request request) {
        this.taskName = request.getArg();
        this.userName = request.getUserName();
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public States getState() {
        return state;
    }

    public void setState(States state) {
        this.state = state;
    }

}
