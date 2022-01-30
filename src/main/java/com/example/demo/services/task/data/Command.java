package com.example.demo.services.task.data;

import com.example.demo.services.task.TaskException;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static com.example.demo.services.task.TaskException.Type.WRONG_FORMAT;
import static com.example.demo.services.task.data.TaskStatus.CLOSED;
import static com.example.demo.services.task.data.TaskStatus.CREATED;
import static org.apache.commons.lang3.StringUtils.isNotBlank;

public class Command {
    private String arg;
    private Type type;

    private Command() {
    }

    public Command(String title, String arg) {
        this.type = checkType(title, arg);

        this.arg = arg != null ? arg : "";
    }

    public String getArg() {
        return arg;
    }

    public Type getType() {
        return this.type;
    }

    private List<String> getTitleTypes() {
        return Arrays
                .stream(Type.values())
                .map(Type::getTitle)
                .collect(Collectors.toList());
    }

    private List<Type> getTypes() {
        return Arrays
                .stream(Type.values())
                .collect(Collectors.toList());
    }

    private Type checkType(String title, String arg) {
        return getTypes()
                .stream()
                .filter(t -> t.getTitle().equals(title) && t.isNeedArg() == isNotBlank(arg))
                .findFirst()
                .orElseThrow(() -> new TaskException(WRONG_FORMAT));
    }

    public enum Type {
        LIST_TASK("LIST_TASK", false, List.of(CREATED, CLOSED)),
        CREATE_TASK("CREATE_TASK", true, List.of(CREATED)),
        DELETE_TASK("DELETE_TASK", true, List.of(CLOSED)),
        CLOSE_TASK("CLOSE_TASK", true, List.of(CREATED)),
        REOPEN_TASK("REOPEN_TASK", true, List.of(CLOSED));

        String title;
        boolean needArg;
        List<TaskStatus> statusList;

        Type(String title, boolean needArg, List<TaskStatus> statusList) {
        }

        public String getTitle() {
            return title;
        }

        public boolean isNeedArg() {
            return needArg;
        }

        public List<TaskStatus> getStatusList() {
            return statusList;
        }
    }
}
