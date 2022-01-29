package com.example.demo;
import java.util.Arrays;
import java.util.Scanner;

public class DemoApplication {

    static String[][] tasks = new String[256][3];
    static String command;

    public static void lineInput(String in)
    {
        
        System.out.println("Enter a command:\nUsers:\n0: Petya\n1: Vasya\n2: Igor\n3: Slava");
               
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
    
    public static void addTask(String username, String taskname) {
        int row = getFreeRow();
        tasks[row][0] = username;
        tasks[row][1] = taskname;
        tasks[row][2] = "CREATED";
    }
    
    public static void findAndCloseOpenTask(String taskname, String setStatus) {
        for (int i=0; i<tasks[1].length; i++) {
            if (taskname.equals(tasks[i][1])) {
                tasks[i][2] = setStatus;
            }
        }
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
                System.out.println("CLOSED");
                findAndCloseOpenTask(arrCommand[2], "CLOSED");
                
            }
            if (sendedCommand.equals("REOPEN_TASK")) {
                System.out.println("Operation will be reopened");
                findAndCloseOpenTask(arrCommand[2], "CREATED");
                
            }
            if (sendedCommand.equals("DELETE_TASK")) {
                System.out.println("Operation will be Deleted");
                findAndCloseOpenTask(arrCommand[2], "DELETED");
            }
        }
    
    public static boolean isTasksEmpty()
    {
        boolean isEmpty = true;
        for (int j=0; j<tasks.length; j++) {
            for (int y=0; y<tasks[j].length;y++) {
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

    public static boolean isCommandCorrect() {
        //если успею сделать проверку входных параметров
        return true;
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
        for (int i=0; i<arrCommand.length; i++) //i=0 - username; i=1 - command; i=3 argument
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
                    if (isCommandCorrect() == true) commandExecute(command);
                }
        in.close();
    }
    
}
