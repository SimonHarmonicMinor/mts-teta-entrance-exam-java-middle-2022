package com.example.demo.task;

import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import com.example.demo.task.entity.User;
import com.example.demo.task.entity.Task;
import com.example.demo.task.status.StatusRulesManager;
import com.example.demo.task.status.StatusTask;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Медеджер для работы с задачами и пользователями.
 * Create by fkshistero on 30.01.2022.
 */
public class TaskManagerImpl implements TaskManager {

    private static final Logger logger = LoggerFactory.getLogger(TaskManagerImpl.class);

    private HashMap<String, User> users = new HashMap<>();

    /**
     * @throws TaskManagerException There is a problem. User already exists.
     */
    @Override
    public User createUser(String userName) throws TaskManagerException {
        if(users.containsKey(userName)) {
            throw new TaskManagerException(TaskManagerError.USER_EXIST, "User already exists.");
        }

        User user =  new User(userName);
        users.put(userName, user);
        return user;
    }


    /**
     * Add a new task for a user.
     * If there is not a user that it creates a new user.
     * @return It is success.
     */
    @Override
    public boolean addTask(String userName, String nameTask) throws TaskManagerException {

        if(!this.isUniqueness(nameTask)) {
            throw new TaskManagerException(TaskManagerError.TASK_EXIST, String.format("Name task '%s' already is taken.", nameTask));
        }

        //If there is not a user that it creates a new user.
        User user = users.get(userName);
        if(Objects.isNull(user)) {
            user = this.createUser(userName);
        }
        Task task = new Task(nameTask);
        user.addTask(task);
        return true;

    }

    @Override
    public List<String> getAllNameTasksOfUser(String userName) throws TaskManagerException {

        if(!this.users.containsKey(userName)) {
            throw new TaskManagerException(TaskManagerError.USER_NOT_EXIST, String.format("There isn't a user '%s'.", userName));
        }

        return this.users.get(userName).getNameTasks();
    }

    @Override
    public boolean deleteTask(String userName, String taskName) throws TaskManagerException {
        return this.changeTaskStatus(userName, taskName, StatusTask.DELETED);
    }

    @Override
    public boolean closeTask(String userName, String taskName) throws TaskManagerException {
        return this.changeTaskStatus(userName, taskName, StatusTask.CLOSED);
    }

    @Override
    public boolean openTask(String userName, String taskName) throws TaskManagerException {
        return this.changeTaskStatus(userName, taskName, StatusTask.CREATED);
    }

    /**
     * It changes a status of a task.
     *
     * @param userName Name of a user.
     * @param taskName Name of a task.
     * @param newStatus New status of a task.
     * @return If it is a successful change, the return will be "true".
     * @throws TaskManagerException There is a problem.
     */
    private boolean changeTaskStatus(String userName, String taskName, StatusTask newStatus) throws TaskManagerException {

        if(!this.users.containsKey(userName)) {
            //Любое изменение может происходить только над своей учеткой.
            throw new TaskManagerException(TaskManagerError.NO_ACCESS,
                    String.format("There isn't access for changing. User doesn't exist: %s.", userName));
        }

        User user = this.users.get(userName);
        //Задачи нет на активном пользователе.
        if(!user.containsTask(taskName)) {
            //Но задача висит на других пользователях.
            if(findTask(taskName).isPresent()) {
                throw new TaskManagerException(TaskManagerError.NO_ACCESS,
                        String.format("There isn't access for changing task (%s) of other user. User: %s.", taskName, userName));
            } else {
                throw new TaskManagerException(TaskManagerError.TASK_NOT_EXIST,
                        String.format("There isn't a task '%s' for a user '%s'.", taskName, userName));
            }
        }

        Task task = user.getTask(taskName);
        if (StatusRulesManager.canChange(task.getStatus(), newStatus)) {
            task.setStatus(newStatus);
            this.ifNeedDeleteTask(user, task);
            return true;
        }else{
            logger.warn(String.format("Can't change status '%s' to '%s'.", task.getStatus(), newStatus));
        }

        return false;
    }


    /**
     * Search for a task from all users.
     *
     * @param nameTask Name of task.
     * @return Task.
     */
    private Optional<Task> findTask(String nameTask){
        for(User user: this.users.values()){
            if(user.containsTask(nameTask)) {
                return Optional.of(user.getTask(nameTask));
            }
        }
        return Optional.empty();
    }


    /**
     * Is deletes a task if a status is DELETED.
     * TODO: В постановке нет информации что эти задачи необходимы для какого либо кейса.
     */
    private void ifNeedDeleteTask(User user, Task task) {
        if (StatusTask.DELETED.equals(task.getStatus())) {
            user.deleteTask(task.getName());
        }
    }


    /**
     * It checks the uniqueness of the task considering all users.
     */
    private boolean isUniqueness(String nameTask) {
        for(User user: this.users.values()) {
            if(user.containsTask(nameTask)) {
                return false;
            }
        }
        return true;
    }

    public boolean containsTask(String firstUser, String taskTest) {
        return this.users.get(firstUser).containsTask(taskTest);
    }
}
