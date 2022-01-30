package com.example.demo;

import com.example.demo.domain.Task;
import com.example.demo.helper.Answer;
import com.example.demo.helper.TaskStatus;
import com.example.demo.service.TaskServiceImpl;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

class TaskServiceTest {

    ArrayList <Task> fillingDB(){
        ArrayList<Task> tasks = new ArrayList<>();
        tasks.add(new Task("task1","user1"));
        tasks.add(new Task("task2","user2"));
        tasks.add(new Task("task3","user3"));
        tasks.add(new Task("task4","user4"));

        tasks.add(new Task("task5", "user5", TaskStatus.CLOSED));
        tasks.add(new Task("task6", "user6", TaskStatus.CLOSED));

        tasks.add(new Task("task7", "user7", TaskStatus.DELETED));
        tasks.add(new Task("task100", "user2", TaskStatus.DELETED));

        tasks.add(new Task("taskForList2", "user2", TaskStatus.CREATED));
        tasks.add(new Task("taskForList1", "user2", TaskStatus.CREATED));

        return tasks;
    }

    @Test
    void testSave(){
        TaskServiceImpl taskService = new TaskServiceImpl();
        taskService.setTaskList(fillingDB());

        assertEquals(Answer.ERROR, taskService.save("task1", "user1"));
        assertEquals(Answer.ERROR, taskService.save("task1", "user2"));
        assertEquals(Answer.ERROR, taskService.save("task5", "user2"));
        assertEquals(Answer.ERROR, taskService.save("task6", "user2"));

        assertEquals(Answer.CREATED, taskService.save("task7", "user1"));
        assertEquals(Answer.CREATED, taskService.save("task8", "user1"));
        assertEquals(Answer.ERROR, taskService.save("task8", "user99"));
        assertEquals(Answer.CREATED, taskService.save("task9", "user99"));
    }

    @Test
    void testClose(){
        TaskServiceImpl taskService = new TaskServiceImpl();
        taskService.setTaskList(fillingDB());

        assertEquals(Answer.ERROR, taskService.close("task10", "user1"));//задачи не существует
        assertEquals(Answer.ACCESS_DENIED, taskService.close("task1", "user2"));//задача создана другим пользователем
        assertEquals(Answer.ERROR, taskService.close("task5", "user5"));//задача с таким именем существует и имеет статус CLOSED

        assertEquals(Answer.CLOSED, taskService.close("task1","user1"));
        assertEquals(Answer.CLOSED, taskService.close("task2","user2"));
    }

    @Test
    void testDelete(){
        TaskServiceImpl taskService = new TaskServiceImpl();
        taskService.setTaskList(fillingDB());

        assertEquals(Answer.ERROR, taskService.delete("task10", "user1"));//задачи не существует
        assertEquals(Answer.ACCESS_DENIED, taskService.delete("task1", "user2"));//задача создана другим пользователем
        assertEquals(Answer.ERROR, taskService.delete("task7", "user7"));//задача удалена = не существует
        assertEquals(Answer.ERROR, taskService.delete("task4", "user4"));//задача с таким именем существует и имеет статус CREATED


        assertEquals(Answer.DELETED, taskService.delete("task5","user5"));
        assertEquals(Answer.DELETED, taskService.delete("task6","user6"));
        assertEquals(Answer.ERROR, taskService.delete("task7","user7"));//задача уже имеет статус DELETED
    }

    @Test
    void testReopen(){
        TaskServiceImpl taskService = new TaskServiceImpl();
        taskService.setTaskList(fillingDB());

        assertEquals(Answer.ERROR, taskService.reopen("task10", "user1"));//задачи не существует
        assertEquals(Answer.ACCESS_DENIED, taskService.reopen("task1", "user2"));//задача создана другим пользователем
        assertEquals(Answer.ERROR, taskService.reopen("task7", "user7"));//задача удалена = не существует
        assertEquals(Answer.ERROR, taskService.reopen("task4", "user4"));//задача с таким именем существует и имеет статус CREATED

        assertEquals(Answer.REOPENED, taskService.reopen("task5","user5"));
        assertEquals(Answer.REOPENED, taskService.reopen("task6","user6"));
        assertEquals(Answer.CLOSED, taskService.close("task6","user6"));
    }

    @Test
    void testSelectAll(){
        TaskServiceImpl taskService = new TaskServiceImpl();
        taskService.setTaskList(fillingDB());

        assertEquals(new ArrayList<>(), taskService.selectAll("1234"));//пользователя не существует

        assertEquals(fillingDB().stream()
                                .filter(t -> !t.getStatus().equals(TaskStatus.DELETED) && t.getUserName().equals("user1"))
                                .collect(Collectors.toList()), taskService.selectAll("user1"));


        List<Task> list1 = new ArrayList<>();
        list1.add(new Task("task2","user2"));
        list1.add(new Task("taskForList2", "user2", TaskStatus.CREATED));
        list1.add(new Task("taskForList1", "user2", TaskStatus.CREATED));

        assertEquals(list1, taskService.selectAll("user2"));

    }
}
