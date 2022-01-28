package controllers;

import model.Task;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class VerificationController {


    /*
     * Данный метод проверяет валидность формата входной строки
     * Строка должна состоять из трех атрибутов
     * Первые два обязательно в Верхнев регистре
     * Третий атрибут при command = 'LIST_TASK', так же должен быть в верхнем регистре
     */
    public boolean validateFormat(String input) {
        List<String> inputArr = Arrays.asList(input.split(" "));
        List<String> appCommand = new ArrayList<>();

        appCommand.add("CREATE_TASK");
        appCommand.add("DELETE_TASK");
        appCommand.add("CLOSE_TASK");
        appCommand.add("REOPEN_TASK");
        appCommand.add("LIST_TASK");



         //Первый if проверяет, что строка пришла с нужным количеством атрибутов
        if (inputArr.size() ==3) {
            String name = inputArr.get(0);
            String command = inputArr.get(1);
            String arg = inputArr.get(2);
            boolean comIsTrue = false;

            for(String com : appCommand) {
                if (com.equals(command)) {
                    comIsTrue  = true;
                    break;
                }
            }

           //Далее проверяем что регистр соблюден
            if(name.equals(name.toUpperCase()) && comIsTrue) {
                //Необходимо еще проверить, что при команде LIST_TASK, аргумент записан в верхнем регисте
                if("LIST_TASK".equals(command)) {
                    if (!arg.equals(arg.toUpperCase())) {
                        return false;
                    }
                    return true;
                }
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    public boolean validateUniqAndNonDeleted(String taskName, ArrayList<Task> tasksCollection) {
        for(Task task : tasksCollection) {
            if(taskName.equals(task.getName())){
                if("DELETED".equals(task.getTaskStatus())) {
                    return true;
                }
                return false;
            }
        }
        return true;

    }

    public boolean validateAccess(String taskName, String userName, ArrayList<Task> tasksCollection) {

        for(Task task : tasksCollection) {
            if(taskName.equals(task.getName()) &&
                    !"DELETED".equals(task.getTaskStatus()) &&
                        userName.equals(task.getCreator())) {
                return true;
            }
        }
        return false;
    }

}
