package com.example.demo.task;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

/**
 * Create by fkshistero on 30.01.2022.
 */
class TaskManagerImplTest {

    private static TaskManagerImpl userManager;

    @BeforeAll
    static void beforeAll() {
        userManager = new TaskManagerImpl();
        for(String userName: UsersMock.users) {
            try {
                userManager.createUser(userName);
            } catch (TaskManagerException e) {
                e.printStackTrace();
            }
        }
    }

    @Test
    void createUser() {
        //Проверить добавление.
        try {
            String userTest = "createUser_User1";
            Assertions.assertEquals(userManager.createUser(userTest).getName(), userTest);
        }catch (TaskManagerException e){
            Assertions.fail();
        }

        //Такой уже есть.
        try {
            userManager.createUser(UsersMock.userFirst());
            Assertions.fail();
        }catch (TaskManagerException e){
            Assertions.assertEquals(e.getError(), TaskManagerError.USER_EXIST);
        }

        //Изменить регистр и добавить снова.
        Assertions.assertDoesNotThrow(()->userManager.createUser(UsersMock.userFirst().toLowerCase()));



    }

    @Test
    void addTask() {
        String firstUser = UsersMock.userFirst();

        //Проверить добавление.
        try {
            String taskTest = "addTask_task1";
            boolean isSuccess = userManager.addTask(firstUser, taskTest);
            Assertions.assertTrue(isSuccess);
            Assertions.assertTrue(userManager.containsTask(firstUser, taskTest));
        }catch (TaskManagerException e){
            Assertions.fail();
        }

        //Такой уже есть.
        try {
            String taskTest = "addTask_task1";
            userManager.addTask(firstUser, taskTest);
            Assertions.fail();
        }catch (TaskManagerException e){
            Assertions.assertEquals(e.getError(), TaskManagerError.TASK_EXIST);
        }

        {
            //Изменить регистр и добавить снова.
            String taskTest = "addTask_task1";
            Assertions.assertDoesNotThrow(() -> userManager.addTask(firstUser, taskTest.toLowerCase()));
        }


        //Проверить добавление, когда пользователя нет.
        try {
            boolean isSuccess = userManager.addTask("addTask_user1", "addTask_task2");
            Assertions.assertTrue(isSuccess);
            Assertions.assertTrue(userManager.containsTask("addTask_user1", "addTask_task2"));
        }catch (TaskManagerException e){
            Assertions.fail();
        }
    }

    @Test
    void getAllNameTasksOfUser() {
    }

    @Test
    void deleteTask() {
        String firstUser = UsersMock.userFirst();
        //Добавим таск для тестирования.
        String taskTest = "deleteTask_task1";
        try {
            userManager.addTask(firstUser, taskTest);
        } catch (TaskManagerException e) {
            Assertions.fail();
        }

        //Удалим только что созданый таск.
        try {
            Assertions.assertFalse(userManager.deleteTask(firstUser, taskTest));
        }catch (TaskManagerException e) {
            Assertions.fail();
        }

        //Попробуем удалить не наш таск.
        try {
            String user = "deleteTask_user2";
            String task = "deleteTask_task2";
            userManager.addTask(user, task);
            userManager.deleteTask(firstUser, task);
        }catch (TaskManagerException e) {
            Assertions.assertEquals(TaskManagerError.NO_ACCESS, e.getError());
        }

        //Переведем таск в состояние close, а затем удалим.
        try {
            userManager.closeTask(firstUser, taskTest);
            Assertions.assertTrue(userManager.deleteTask(firstUser, taskTest));
        }catch (TaskManagerException e) {
            Assertions.fail();
        }
    }

    @Test
    void closeTask() {

        String firstUser = UsersMock.userFirst();
        //Добавим таск для тестирования.
        String taskTest = "closeTask_task1";
        try {
            userManager.addTask(firstUser, taskTest);
        } catch (TaskManagerException e) {
            Assertions.fail();
        }

        //Закроем только что созданый таск.
        try {
            Assertions.assertTrue(userManager.closeTask(firstUser, taskTest));
        }catch (TaskManagerException e) {
            Assertions.fail();
        }

        //Попробуем закрыть не наш таск.
        try {
            String user = "closeTask_user2";
            String task = "closeTask_task2";
            userManager.addTask(user, task);
            userManager.closeTask(firstUser, task);
        }catch (TaskManagerException e) {
            Assertions.assertEquals(TaskManagerError.NO_ACCESS, e.getError());
        }

        //Переведем таск в состояние create, а затем закроем.
        try {
            userManager.openTask(firstUser, taskTest);
            Assertions.assertTrue(userManager.closeTask(firstUser, taskTest));
        }catch (TaskManagerException e) {
            Assertions.fail();
        }
    }

    @Test
    void openTask() {

        String firstUser = UsersMock.userFirst();
        //Добавим таск для тестирования.
        String taskTest = "openTask_task1";
        try {
            userManager.addTask(firstUser, taskTest);
        } catch (TaskManagerException e) {
            Assertions.fail();
        }

        //Попробуем открыть несуществующий таск.
        try {
            String task = "openTask_task2";
            userManager.openTask(firstUser, task);
        }catch (TaskManagerException e) {
            Assertions.assertEquals(TaskManagerError.TASK_NOT_EXIST, e.getError());
        }

        //Переведем таск в состояние close, а затем open.
        try {
            userManager.closeTask(firstUser, taskTest);
            Assertions.assertTrue(userManager.openTask(firstUser, taskTest));
        }catch (TaskManagerException e) {
            Assertions.fail();
        }
    }
}