package com.example.demo;

class Task {

    private String userName;
    private String taskName;
    private String taskState;

    String getUserName() {
        return userName;
    }


    String getTaskName() {
        return taskName;
    }


    String getTaskState() {
        return taskState;
    }

    private void setTaskState(String taskState) {
        this.taskState = taskState;
    }


    Task(String[] param) {
        this.userName = param[0];
        this.taskName = param[2];
        this.taskState = String.valueOf(TaskState.CREATED);
    }


    TaskResponse closeTask() {

        if (this.getTaskState().equals(String.valueOf(TaskState.CREATED))) {
            setTaskState(String.valueOf(TaskState.CLOSED));
            return TaskResponse.CLOSED;
        }
        return TaskResponse.ERROR;
    }


    TaskResponse reopenTask() {
        if (this.getTaskState().equals(String.valueOf(TaskState.CLOSED))) {
            setTaskState(String.valueOf(TaskState.CREATED));
            return TaskResponse.REOPENED;
        }
        return TaskResponse.ERROR;
    }

}
