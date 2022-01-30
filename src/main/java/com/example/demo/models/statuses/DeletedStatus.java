package com.example.demo.models.statuses;

import com.example.demo.models.Task;

public class DeletedStatus extends Status {

    public final static int ID = 3;

    public DeletedStatus(Task task) {
        super(task);
    }

    @Override
    public Boolean next() {
        return false;
    }

    @Override
    public Boolean previous() {
        return false;
    }

    @Override
    public int getId() {
        return ID;
    }
}
