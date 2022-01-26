package com.example.demo;

public class Tasklist {
    // обьект элемент списка задач
    //задача и пользователь
    public User user;
    public Task task;

    public void setTask(Task task2) {
        this.task = task2;
    }

    public void setUser(User user2) {
        this.user = user2;

    }
    // выводим норм имя обьекта
    @Override
    public String toString() {
        return task.taskname;
    }
}
