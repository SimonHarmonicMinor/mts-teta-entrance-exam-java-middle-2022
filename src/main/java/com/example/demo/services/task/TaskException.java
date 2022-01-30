package com.example.demo.services.task;

import static com.example.demo.services.task.TaskException.Type.DEFAULT;

public class TaskException extends RuntimeException {

    public TaskException() {
        this(DEFAULT);
    }

    public TaskException(Type type) {
        super(type.getMessage());
    }

    public enum Type {
        WRONG_FORMAT("WRONG_FORMAT"),
        ACCESS_DENIED("ACCESS_DENIED"),
        DEFAULT("ERROR");

        private final String message;

        Type(String message) {
            this.message = message;
        }

        public String getMessage() {
            return message;
        }
    }

}
