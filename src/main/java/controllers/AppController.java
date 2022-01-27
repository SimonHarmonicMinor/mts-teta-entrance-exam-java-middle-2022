package controllers;

import model.Task;
import java.util.ArrayList;
import java.util.List;

public class AppController {
    private ArrayList<Task> tasksCollection = new ArrayList<>();
    VerificationController verificationController = new VerificationController();


    /**
     * Метод позволяет создать новую запись в коллекции taskCollection
     * На вход принимает название записи и автора
     * @param creatorName
     * @param taskName
     * @return ERROR or CREATED
     */
    public String createTask(String creatorName, String taskName) {

        //Проверка на наличие дублей в коллекции
        if(!verificationController.validateUniqAndNonDeleted(taskName,tasksCollection)) {
            return "ERROR";
        }

        Task task = new Task();
        task.setCreator(creatorName);
        task.setName(taskName);
        task.setTaskStatus("CREATED");
        tasksCollection.add(task);
        return "CREATED";
    }


    /**
     * Метод позволяет выставлять статус "DELETED" задачам
     * Данный метод может выполнить только создатель задачи
     * @param userName
     * @param taskName
     * @return ACCESS_DENIED or DELETED
     */
    public  String deleteTask(String userName, String taskName) {
        if(!verificationController.validateAccess(taskName,userName, tasksCollection)) {
            return "ACCESS_DENIED";
        }
        for(Task task : tasksCollection) {
            if (task.getName().equals(taskName)) {
                if (!"CREATED".equals(task.getTaskStatus())) {
                    task.setTaskStatus("DELETED");
                    return "DELETED";
                } else {
                    return "ERROR";
                }
            }
        }
        return "ERROR";
    }

    /**
     * Метод позволяет закрыть задачу, в слуае когда ее статус CREATED
     * В случае, когда статус задачи CLOSED, возвращает ERROR
     * В случае, когда статус задачи DELETED, возвращает ACCESS_DENIED, так как доступа к этой задачи нет
     * @param userName
     * @param taskName
     * @return
     */
    public  String closeTask(String userName, String taskName) {
        if (!verificationController.validateAccess(taskName, userName, tasksCollection)) {
            return "ACCESS_DENIED";
        }
        for (Task task : tasksCollection) {
            if (task.getName().equals(taskName)) {
                if (!"CLOSED".equals(task.getTaskStatus())) {
                    task.setTaskStatus("CLOSED");
                    return "CLOSED";
                } else {
                    return "ERROR";
                }
            }
        }
        return "ERROR";
    }

    /**
     * Метод позволяет открыть заново задачу если ее статус CREATED
     * В случае если, когда статус задачи CLOSED, возвращает ERROR
     * В случае, когда статус задачи DELETED, возвращает ACCESS_DENIED, так как доступа к этой задачи нет
     * @param userName
     * @param taskName
     * @return
     */
    public  String reopenTask(String userName, String taskName) {
        if (!verificationController.validateAccess(taskName, userName, tasksCollection)) {
            return "ACCESS_DENIED";
        }
        for (Task task : tasksCollection) {
            if (task.getName().equals(taskName)) {
                if(!"CREATED".equals(task.getTaskStatus())) {
                    task.setTaskStatus("REOPENED");
                    return "REOPENED";
                } else {
                    return "ERROR";
                }
            }
        }
        return "ERROR";
    }

    /**
     * Метод позволяет вернуть список задач конкретного пользователя
     * Возвращет только активные задачи, которые находятся не в статусе DELETED
     * @param creatorName
     * @return
     */
    public List<String> showTasksList(String creatorName) {
        List<String> userTasks = new ArrayList<>();
        for(Task task : tasksCollection) {
            if (!"DELETED".equals(task.getTaskStatus())  && task.getCreator().equals(creatorName)) {
                userTasks.add(task.getName());
            }
        }
        return userTasks;
    }
}
