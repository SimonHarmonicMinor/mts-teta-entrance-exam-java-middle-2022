package com.example.demo.services.task.components;

import com.example.demo.services.task.TaskException;

import java.util.Arrays;
import java.util.List;

import static com.example.demo.services.task.TaskException.Type.WRONG_FORMAT;
import static com.example.demo.services.task.components.TaskStatus.*;
import static org.apache.commons.lang3.StringUtils.isNotBlank;

public class Command {
    private String arg;
    private Type type;

    private Command() {
    }

    public Command(String title, String arg) throws TaskException {
        this.type = checkType(title, arg);
        this.arg = arg != null ? arg : "";
    }

    public String getArg() {
        return arg;
    }

    public Type getType() {
        return this.type;
    }

    private Type checkType(String title, String arg) throws TaskException {
        return Arrays.stream(Type.values())
                .filter(t ->
                        t.getTitle().equals(title)
                                && (t.isNeedArg() == isNotBlank(arg) || title.equals(Type.LIST_TASK.getTitle()))
                )
                .findFirst()
                .orElseThrow(() -> new TaskException(WRONG_FORMAT));
    }

    public enum Type {
        LIST_TASK("LIST_TASK", false, List.of(CREATED, CLOSED, REOPENED)),
        CREATE_TASK("CREATE_TASK", true, List.of()),
        DELETE_TASK("DELETE_TASK", true, List.of(CLOSED)),
        CLOSE_TASK("CLOSE_TASK", true, List.of(CREATED, REOPENED)),
        REOPEN_TASK("REOPEN_TASK", true, List.of(CLOSED));

        final String title;
        final boolean needArg;
        final List<TaskStatus> statusList;

        Type(String title, boolean needArg, List<TaskStatus> statusList) {
            this.title = title;
            this.needArg = needArg;
            this.statusList = statusList;
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
