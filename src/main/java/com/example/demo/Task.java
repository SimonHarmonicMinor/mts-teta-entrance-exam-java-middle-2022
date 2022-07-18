package com.example.demo;

public class Task {
    String name;
    String owner;
    state st;

    public Task(String n, String u) {
        this.name = n;
        this.owner = u;
        this.st = state.CREATED;
    }


    public Result Close_task(){
        Result res = new Result();
        if (this.st == state.CREATED)
         {
            this.st = state.CLOSED;
            res.resp = response.CLOSED;
        } else
            res.resp = response.ACCESS_DENIED;
        return res;
    }

    public Result Delete_task(){
        Result res = new Result();
        if (this.st == state.CLOSED)
        {
            this.st = state.DELETED;
            res.resp = response.DELETED;
        } else
            res.resp = response.ACCESS_DENIED;
        return res;
    }

    public Result Reopen_task(){
        Result res = new Result();
        if (this.st == state.CLOSED)
        {
            this.st = state.CREATED;
            res.resp = response.REOPENED;
        } else
            res.resp = response.ACCESS_DENIED;
        return res;
    }
}
enum state{
    CREATED,
    CLOSED,
    DELETED
}