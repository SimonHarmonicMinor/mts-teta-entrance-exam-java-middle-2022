package com.example.demo.helpers;

import java.util.Optional;

public enum TaskState {
    CREATED {
        @Override
        public Optional<TaskState> close() {
            return Optional.of(CLOSED);
        }

        @Override
        public Optional<TaskState> reopen() {
            return Optional.empty();
        }

        @Override
        public Optional<TaskState> delete() {
            return Optional.empty();
        }
    },
    CLOSED {
        @Override
        public Optional<TaskState> close() {
            return Optional.empty();
        }

        @Override
        public Optional<TaskState> reopen() {
            return Optional.of(CREATED);
        }

        @Override
        public Optional<TaskState> delete() {
            return Optional.of(DELETED);
        }
    },
    DELETED {
        @Override
        public Optional<TaskState> close() {
            return Optional.empty();
        }

        @Override
        public Optional<TaskState> reopen() {
            return Optional.empty();
        }

        @Override
        public Optional<TaskState> delete() {
            return Optional.empty();
        }
    };

    public abstract Optional<TaskState> close();
    public abstract Optional<TaskState> reopen();
    public abstract Optional<TaskState> delete();
}
