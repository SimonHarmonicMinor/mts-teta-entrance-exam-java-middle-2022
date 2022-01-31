package com.example.demo;


public enum TaskState {
    CREATED, CLOSED, DELETED, REOPENED, ERROR, ACCESS_DENIED, WRONG_FORMAT;

    public TaskState getNext() {
        switch(this) {
            case CREATED:
            case REOPENED:
                return CLOSED;
            case CLOSED:
                return DELETED;
            default:
                return ERROR;
        }
    }
    public TaskState getPrev() {
        switch(this) {

            case CLOSED:
                return REOPENED;
            default:
                return ERROR;
        }
    }
}
