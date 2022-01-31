package Models;

public class Commands {
    final static String created = "CREATED";
    final static String deleted = "DELETED";
    final static String closed = "CLOSED";
    final static String reopened = "REOPENED";
    final static String tasks = "TASKS";
    final static String wrongFormat = "WRONG_FORMAT";
    final static String accessDenied = "ACCESS_DENIED";
    final static String error = "ERROR";

    public String getCreated() {
        return created;
    }

    public String getDeleted() {
        return deleted;
    }

    public String getClosed() {
        return closed;
    }

    public String getReopened() {
        return reopened;
    }

    public String getTasks() {
        return tasks;
    }

    public String getWrongFormat() {
        return wrongFormat;
    }

    public String getAccessDenied() {
        return accessDenied;
    }

    public String getError() {
        return error;
    }
}
