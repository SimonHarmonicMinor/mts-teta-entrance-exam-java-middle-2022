package com.example.demo.model;

import java.util.Objects;

public final class Task {
    private final String name;
    private final User owner;
    private boolean opened = true;

    public Task(String name, User owner) {
        this.name = Objects.requireNonNull(name, "Task name is null");
        this.owner = Objects.requireNonNull(owner, "Task owner is null");
    }

    public void close() {
        opened = false;
    }

    public void open() {
        opened = true;
    }

    public String getName() {
        return name;
    }

    public User getOwner() {
        return owner;
    }

    public boolean isOpened() {
        return opened;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Task)) {
            return false;
        }
        return Objects.equals(name, ((Task) o).getName());
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }
}
