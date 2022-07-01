package com.example.demo;

 public  class Handler {
     public enum Commands
     {
         CREATE_TASK, CLOSE_TASK, DELETE_TASK, REOPEN_TASK, LIST_TASK
     }
    public static String getResponse(String line){
        String result = null;
        if ("__DELETE_ALL".equals(line))  {
            result = TasksList.deleteAll();
            return result;
        }

        String[] rq_parts = line.split(" ");
        if ( rq_parts.length < 2 || rq_parts.length > 3 ) {
            return "WRONG_FORMAT";
        }
        String arg = null;
        if (rq_parts.length == 3)
        {
            arg =  rq_parts[2];
        }
        Request rq = new Request();
        rq.setParams(rq_parts[0],rq_parts[1],arg);
        try {
            Commands.valueOf(rq.command);
        }
        catch (IllegalArgumentException e) {
            return "WRONG_FORMAT";
        }
        Task task = new Task();
       switch(rq.command){
           case  "CREATE_TASK":
               if (rq.arg == null) {
                    return "WRONG_FORMAT";
                }
               task.setParams(rq.arg, rq.user , "CREATED");
               result = TasksList.createTask(task);
               break;
           case  "CLOSE_TASK":
               task.setParams(rq.arg, rq.user , "CLOSED");
               result = TasksList.closeTask(task);
               break;
           case  "DELETE_TASK":
               task.setParams(rq.arg, rq.user , "DELETED");
               result = TasksList.deleteTask(task);
               break;
           case  "REOPEN_TASK":
               task.setParams(rq.arg, rq.user , "CREATED");
               result = TasksList.reopenTask(task);
               break;
           case  "LIST_TASK":
               result = "TASKS " + TasksList.listTask(rq.arg);
               break;
       }
        return result;
    }

}
