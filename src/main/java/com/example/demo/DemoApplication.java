package com.example.demo;
import java.util.Arrays;
import java.util.Scanner;

public class DemoApplication {

    public static String[][] tasks = new String[256][3]; //Таблица тасков где 3 столбца: user name / task name / status of task
    public static String command;
    public static String access_result = "";
    
    public static boolean isCommandOrderCorrect(String commandUser, String commandTaskName, String commandArg) {
        for (int i=0; i<tasks.length; i++) {
            if (tasks[i][1]!=null && tasks[i][1].equals(commandTaskName))
            {
                //Current row in table tasks
                if (tasks[i][2].equals("DELETED")) return false; //если в таблице таск со статусом DELETED мы уже ничего не можем с ним сделать и всё отвергаем
                if (tasks[i][2].equals("CREATED") && commandArg.equals("DELETE_TASK")) return false;
            }
        }
        return true;
    }
    
    public static String fingUniqTaskIndexForDelete(String taskname) {
        int delete_id = 0;
        String[] arrTask = {};
        for (int i=0; i<tasks.length; i++) {
            if (tasks[i][1]!=null) {
                arrTask = tasks[i][1].split("___");
                if (arrTask.length > 1 && Integer.parseInt(arrTask[1])+1 > delete_id) delete_id = Integer.parseInt(arrTask[1])+1;    
            }
        }
        return taskname+"___"+delete_id;
    }
    
    public static int getFreeRow() {
        int row_id = 0;
            for (int j=0; j<tasks.length; j++) {
                int isNull = 0;
                String row = Arrays.toString(tasks[j]);
                for (int y=0; y<tasks[0].length;y++) {
                    if (tasks[j][y] != null) isNull = 1;
                    }
                    if (isNull ==0) {
                        row_id = j;
                        break;
                    }
            }
        return row_id;
    }
    
    public static void showTaskList(String[] arrCommand) {
        for (int j=0; j<tasks.length; j++) {
            if (tasks[j][0]!=null && tasks[j][0].equals(arrCommand[2]) && tasks[j][2]!="DELETED") { //если элемент таблицы tasks не null И имя (аргумент) = имя автора задачи И статус != DELETED
                System.out.println(tasks[j][1]+" "+tasks[j][2]);
            } 
        }
    }
    
    public static void addTask(String username, String taskname) {
        int row = getFreeRow();
        tasks[row][0] = username;
        tasks[row][1] = taskname;
        tasks[row][2] = "CREATED";
    }
      
    public static void findAndCloseOpenTask(String taskname, String setStatus, String[] arrCommand) {
        for (int i=0; i<tasks.length; i++) {
            if (taskname.equals(tasks[i][1]) && tasks[i][0].equals(arrCommand[0]) && (setStatus.equals("CLOSED") || setStatus.equals("CREATED") || setStatus.equals("DELETED"))) { //имя таска = таск из таблицы И юзер из строки с таском = юзер из команды И присваеваеый статус CLOSED||CREATE||DELETED
                if (isCommandOrderCorrect(arrCommand[0], taskname, arrCommand[1]) == true) {
                    access_result = setStatus;
                    tasks[i][2] = setStatus;
                    if (setStatus.equals("DELETED")) tasks[i][1] = fingUniqTaskIndexForDelete(taskname);
                    break;
                } else {
                    access_result = "WRONG REQUEST";
                    break;
                }

            } else {
                access_result = "ACCESS DENIED OR TASK NOT FOUND";
            }
        }
        if (access_result.equals("CREATED")) access_result = "REOPENED";
        System.out.println(access_result);
    }
    
    public static void action(String sendedCommand, String[] arrCommand)
        {
            if (sendedCommand.equals("CREATE_TASK")) {
                if (isTaskExist(arrCommand[2]) == false) {
                System.out.println("CREATED");
                addTask(arrCommand[0], arrCommand[2]);    
                } else {
                    System.out.println("TASK WITH THIS NAME WAS ALREADY CREATED!");
                }
            }
            if (sendedCommand.equals("CLOSE_TASK")) {
                findAndCloseOpenTask(arrCommand[2], "CLOSED", arrCommand);
                
            }
            if (sendedCommand.equals("REOPEN_TASK")) {
                findAndCloseOpenTask(arrCommand[2], "CREATED", arrCommand);
                
            }
            if (sendedCommand.equals("DELETE_TASK")) {
                findAndCloseOpenTask(arrCommand[2], "DELETED",  arrCommand);
            }
            if (sendedCommand.equals("LIST_TASK")) {
                showTaskList(arrCommand);
            }
        }
    
    public static boolean isTasksEmpty()
    {
        boolean isEmpty = true;
        for (int j=0; j<tasks.length; j++) {
            for (int y=0; y<tasks[0].length;y++) {
            if (tasks[j][y] != null) {
                isEmpty = false;
                }
            }
        }
        return isEmpty;
    }
    
    public static void showTasksTable() //служебная функция для просмотра таблицы тасков
    {
        for (int j=0; j<tasks.length; j++) {
            int isNull = 0;
            String row = Arrays.toString(tasks[j]);
            for (int y=0; y<tasks[0].length;y++) {
            if (tasks[j][y] != null) isNull = 1;
            }
            if (isNull !=0) System.out.println(row);
        }
    }

    public static boolean isCommandCorrect(String command) {
        String[] commands = {"CREATE_TASK", "CLOSE_TASK", "REOPEN_TASK", "LIST_TASK", "DELETE_TASK"};
        String[] arrCommand = command.split(" ");
        if (arrCommand.length == 1 && (arrCommand[0].equals("exit") || arrCommand[0].equals("TASKS"))) return true;
        if (arrCommand.length == 3 && (Arrays.asList(commands).contains(arrCommand[1]))) return true;
        return false;
    }
    
    public static boolean isTaskExist(String taskname) {
        boolean result = false;
        for (int j=0; j<tasks.length; j++) {
            for (int y=0; y<tasks[0].length;y++) {
            if (taskname.equals(tasks[j][y])) result = true;
            }
        }
        return result;
    }

    public static void commandExecute(String command) {
        String[] arrCommand = command.split(" ");        
        for (int i=0; i<arrCommand.length; i++) //i=0 - username; i=1 - command; i=2 argument
        {
            if (arrCommand[0].equals("exit")) System.exit(0);
            if (arrCommand[0].equals("TASKS")) {
                if (isTasksEmpty() == true) {
                    System.out.println("TASKS IS EMPTY!");
                } else {
                    System.out.println("SOMETHING IN TASKS EXIST!!!");
                    showTasksTable();
                }
            }
            if (i==1)
            {
                action(arrCommand[i], arrCommand);
            }            
        }
    }
        
    public static void main(String[] args) 
    {
        System.out.println("Enter a command with syntax \"user command arg\" or \"exit\": ");
        Scanner in = new Scanner(System.in);
        while(in.hasNextLine())
                {
                    command = in.nextLine();
                    if (isCommandCorrect(command) == true) {
                        commandExecute(command);
                    } else {
                        System.out.println("WRONG COMMAND");
                    }
                }
        in.close();
    }
    
}
