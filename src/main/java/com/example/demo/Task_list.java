package com.example.demo;

import java.util.ArrayList;

public class Task_list {
    ArrayList tlist;

    public Task_list() {
        this.tlist = new ArrayList();
    }

    public static Task Search(ArrayList tl, String task_name) {
        for (int i = 0; i < tl.size(); i++) {
            Task tmp_task = (Task) tl.get(i);
              if (tmp_task.name.equals(task_name) ) {
                return tmp_task;
            }
        }
        return null;
    }


    static Result create_task(Task_list tl, String n, String u){
        Result result = new Result();
        if (Search(tl.tlist, n) == null) {
            tl.tlist.add(new Task(n,u));
            result.resp = response.CREATED;
        } else
            result.resp = response.ERROR;
        return result;

    }

    Result Close_task(String n, String u){
        Result result = new Result();
        Task task = Search(this.tlist, n);
        if (task != null)   {
           if(task.owner.equals(u))
           {
               task.Close_task();
                result.resp = response.CLOSED;
           } else
               result.resp = response.ACCESS_DENIED;
        } else
            result.resp = response.ERROR;
        return result;

    }

    Result Delete_task( String n, String u){
        Result result = new Result();
        Task tmp_task = Search(this.tlist, n);
        if (tmp_task != null)   {
            if (tmp_task.owner == u)
            {
                result = tmp_task.Delete_task();
            } else
                result.resp = response.ACCESS_DENIED;
        } else
            result.resp = response.ERROR;
        return result;

    }

    Result reopen_task( String n, String u){
        Result result = new Result();
        Task task = Search(this.tlist, n);
        if (task != null)  {
            if(task.owner.equals(u))
                result = task.Reopen_task();
             else
                result.resp = response.ACCESS_DENIED;
        } else
            result.resp = response.ERROR;
        return result;

    }

    Result get_task_list( String u){
        Result result = new Result();
        result.resp = response.TASKS;
        result.arg = "[";

        for (int i = 0; i < this.tlist.size(); i++) {
            Task tmp_task = (Task) this.tlist.get(i);
            if (tmp_task.owner.equals(u) && (tmp_task.st == state.CREATED || tmp_task.st == state.CLOSED )) {
                if (!result.arg.equals("["))
                {
                    result.arg = result.arg + ", ";
                }
                result.arg = result.arg + tmp_task.name;
            }
        }
        result.arg = result.arg + "]";
        return result;

    }

    void delete_all(){
     this.tlist.clear();
    }
}
