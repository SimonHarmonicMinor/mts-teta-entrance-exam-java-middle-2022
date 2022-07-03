package com.example.demo;

public class Command {
    public static  Result proc_command(String commandline) {
        System.out.println(commandline);
        String arg= new String();
        String a[] = commandline.split(" ");
        Result res = Command.proc_command(a[1], a[0], a[2]);
        return res;
    }
    public static  Result proc_command(String com, String u, String arg)
    {
        Result result = new Result();
        type_command tc;
        try {
             tc = type_command.valueOf(com);
        }
        catch (IllegalArgumentException e) {
            tc = type_command.__DELETE_ALL;
            result.resp = response.WRONG_FORMAT;
        }
        if (result.resp == null)
            switch(tc){
                case CREATE_TASK:
                    result = Task_list.create_task(Server.t, arg, u);
                    break;
                case CLOSE_TASK:
                    result = Server.t.Close_task( arg, u);
                    break;
                case DELETE_TASK:
                    result = Server.t.Delete_task(arg, u);
                    break;
                case REOPEN_TASK:
                    result = Server.t.reopen_task(arg, u);
                    break;
                case LIST_TASK:
                    result = Server.t.get_task_list(arg);
                    break;
                case __DELETE_ALL:
                    Server.t.delete_all();
                    break;
            }
        return result;
    }
}


enum type_command{
    CREATE_TASK,
    CLOSE_TASK,
    DELETE_TASK,
    REOPEN_TASK,
    LIST_TASK,
    __DELETE_ALL
}


