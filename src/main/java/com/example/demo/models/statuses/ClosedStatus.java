package com.example.demo.models.statuses;

import com.example.demo.models.Task;

public class ClosedStatus extends Status {

    public final static int ID = 2;

    public ClosedStatus(Task task) {
        super(task);
    }

    @Override
    public Boolean next() {
        this.getTask().setStatus(new DeletedStatus(this.getTask()));
        return true;
    }

    @Override
    public Boolean previous() {
        this.getTask().setStatus(new CreatedStatus(this.getTask()));
        return true;
    }

    @Override
    public int getId() {
        return ID;
    }
}
