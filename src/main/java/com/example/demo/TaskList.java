package com.example.demo;

import java.util.ArrayList;

public class TaskList extends ArrayList<Task>
{
    private static TaskList storage = new TaskList();

    public static TaskList getReference()
    {
        return storage;
    }

    private TaskList()
    {
        super();
    }

    private Task getItemByTaskName(String iTaskName)
    {
        Task r;
        for (int i = 0; i < this.size(); i++)
        {
            r = this.get(i);
            if (iTaskName.equals(r.TaskName))
            {
                return r;
            }
        }
        return null;
    }
    public String execute(String iCommand)
    {
        try {
            if (iCommand.equals("__DELETE_ALL")) {
                return deleteAll();
            } else {
                String[] str = iCommand.split(" ");
                if (str.length != 3) {
                    return "WRONG_FORMAT";
                }

                String user = str[0];
                String command = str[1];
                String arg = str[2];


                switch (command)
                {
                    case ("CREATE_TASK"):
                        return createTask(arg,user);

                    case ("CLOSE_TASK"):
                        return closeTask(arg,user);

                    case ("DELETE_TASK"):
                        return deleteTask(arg,user);

                    case ("REOPEN_TASK"):
                        return reopenTask(arg,user);

                    case ("LIST_TASK"):
                        return listTask(arg,user);


                }
            }
            return "WRONG_FORMAT";
        }
        catch (Exception e)
        {
            return "ERROR";
        }
    }

    public String createTask(String iTaskName, String iUserName)
    {
        Task task = getItemByTaskName(iTaskName);
        if (task == null)
        {
            this.add(new Task(iTaskName, iUserName));
            return "CREATED";
        }
        else {
            return "ERROR";
        }
    }

    public String closeTask(String iTaskName, String iUserName)
    {
        Task task = getItemByTaskName(iTaskName);
        if (task != null)
        {
            if (iUserName.equals(task.UserName))
            {
                if (task.Status.equals("CREATED")) {
                    task.Status = "CLOSED";
                    return "CLOSED";
                }
                else {
                    return "ERROR";
                }
            }
            else {
                return "ACCESS_DENIED";
            }
        }
        else {
            return "ERROR";
        }
    }

    public String deleteTask(String iTaskName, String iUserName)
    {
        Task task = getItemByTaskName(iTaskName);
        if (task != null)
        {
            if (iUserName.equals(task.UserName))
            {
                if (task.Status.equals("CLOSED")) {
                    this.remove(task);
                    return "DELETED";
                }
                else {
                    return "ERROR";
                }
            }
            else {
                return "ACCESS_DENIED";
            }
        }
        else {
            return "ERROR";
        }

    }

    public String listTask(String iTaskUserName, String iUserName)
    {
        String retVal = "";
        Task r;
        for (int i = 0; i < this.size(); i++)
        {
            r = this.get(i);
            if (iTaskUserName.equals(r.UserName))
            {
                retVal = retVal + r.TaskName + ", ";
            }
        }
        if (retVal.length() > 1)
        {
            retVal = retVal.substring(0, retVal.length()-2) ;
        }
        return "TASKS [" + retVal + "]";
    }

    public String reopenTask(String iTaskName, String iUserName)
    {
        Task task = getItemByTaskName(iTaskName);
        if (task != null)
        {
            if (iUserName.equals(task.UserName))
            {
                if (task.Status.equals("CLOSED")) {
                    task.Status = "CREATED";
                    return "REOPENED";
                }
                else {
                    return "ERROR";
                }
            }
            else {
                return "ACCESS_DENIED";
            }
        }
        else {
            return "ERROR";
        }

    }

    public String deleteAll()
    {
        for (int i = this.size() - 1; i >= 0; i--)
        {
            this.remove(i);
        }
        return "";
    }
}
