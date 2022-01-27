package controllers;

import model.Task;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class VerificationController {


    /**
     * Данный метод проверяет валидность формата входной строки
     * Строка должна состоять из трех атрибутов
     * Первые два обязательно в Верхнев регистре
     * Третий атрибут при command = 'LIST_TASK', так же должен быть в верхнем регистре
     * @param input
     * @return true or false
     */
    public boolean validateFormat(String input) {
        List<String> inputArr = Arrays.asList(input.split(" "));

         //Первый if проверяет, что строка пришла с нужным количеством атрибутов
        if (inputArr.size() ==3) {
            String name = inputArr.get(0);
            String command = inputArr.get(1);
            String arg = inputArr.get(2);

           //Далее проверяем что регистр соблюден
            if(name.equals(name.toUpperCase()) && ("CREATE_TASK,DELETE_TASK,CLOSE_TASK,REOPEN_TASK,LIST_TASK").contains(command)) {
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
