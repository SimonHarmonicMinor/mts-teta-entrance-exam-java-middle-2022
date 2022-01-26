package com.example.task;

import com.example.response.Response;

public class Task {
    public enum TaskStatuses {CREATED, DELETED, CLOSED}


    public Task(String name, String createdBy) {
        Name = name;
        TaskStatuses = TaskStatuses.CREATED;
        CreatedBy = createdBy;
    }

    private String Name;
    private TaskStatuses TaskStatuses;
    private String CreatedBy;

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public TaskStatuses getStatus() {
        return TaskStatuses;
    }

    public void setStatus(TaskStatuses taskStatuses, Response response) throws Exception {
        try{
            if (this.TaskStatuses == TaskStatuses.DELETED)
                throw new Exception("Deleted status cannot be changed");
            if (this.TaskStatuses == TaskStatuses.CREATED && taskStatuses == TaskStatuses.DELETED)
                throw new Exception("CREATED status cannot be changed to DELETED");
            TaskStatuses = taskStatuses;
        }
        catch (Exception ex){
            response.setResponseCode(Response.ResponseCodes.ERROR);
            throw ex;
        }

    }

    public String getCreatedBy() {
        return CreatedBy;
    }

    public void setCreatedBy(String createdBy) {
        CreatedBy = createdBy;
    }
}
