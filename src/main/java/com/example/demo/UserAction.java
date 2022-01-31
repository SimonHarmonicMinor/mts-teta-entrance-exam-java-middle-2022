package com.example.demo;
public enum UserAction {
    CREATE(TaskState.CREATED), CLOSE(TaskState.CLOSED), DELETE(TaskState.DELETED), REOPEN(TaskState.REOPENED);
    private TaskState state;
    UserAction(TaskState state) {
        this.state = state;
    }
    TaskState getState(){
        return this.state;
    }
}