package com.example.demo.models.statuses;

import com.example.demo.models.Task;

public class CreatedStatus extends Status {

    public final static int ID = 1;

    public CreatedStatus(Task task) {
        super(task);
    }

    @Override
    public Boolean next() {
        this.getTask().setStatus(new ClosedStatus(this.getTask()));
        return true;
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
