package com.example.demo.model;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

public final class Command {
    private final User user;
    private final Type type;
    private final String argument;

    public Command(User user, Type type, String argument) {
        this.user = Objects.requireNonNull(user, "Command user is null");
        this.type = Objects.requireNonNull(type, "Command type is null");
        this.argument = Objects.requireNonNull(argument, "Command argument is null");
    }

    public Type getType() {
        return type;
    }

    public User getUser() {
        return user;
    }

    public String getArgument() {
        return argument;
    }

    public enum Type {
        CREATE_TASK("CREATE_TASK"),
        DELETE_TASK("DELETE_TASK"),
        CLOSE_TASK("CLOSE_TASK"),
        REOPEN_TASK("REOPEN_TASK"),
        LIST_TASK("LIST_TASK");

        private final String name;

        private static final Map<String, Type> typeByName = new HashMap<>();
        static {
            for (Type t : Type.values()) {
                typeByName.put(t.getName(), t);
            }
        }

        Type(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }

        public static Optional<Type> findByName(String name) {
            return name != null ? Optional.ofNullable(typeByName.get(name)) : Optional.empty();
        }
    }
}
