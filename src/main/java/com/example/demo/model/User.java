package com.example.demo.model;

import java.util.Objects;

public final class User {
    private final String name;

    public User(String name) {
        this.name = Objects.requireNonNull(name, "User name is null");
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof User)) {
            return false;
        }
        return Objects.equals(name, ((User) o).getName());
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }

    @Override
    public String toString() {
        return name;
    }
}
