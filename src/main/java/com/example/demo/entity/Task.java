package com.example.demo.entity;

import com.example.demo.entity.enums.TaskState;

/**
 * Сущность описывающие задание со статусом
 * Ряд полей задаются один раз, так как изменение не предусмотрено фичами задания
 * Унаследовано от {@link AbstractTask}
 */
public class Task extends AbstractTask{

    /**
     * Статус задания
     */
    private TaskState state;

    public Task(String name, String userName){
        super(name, userName);
        this.state = TaskState.CREATED;
    }

    /**
     * Сеттер статуса. Дополнительно ревлизована проверка перехода статусов
     * @param state статус
     * @throws Exception ошибка при изменении статуса
     */
    public void setState(TaskState state) throws Exception{
        if(this.state == TaskState.CREATED && state == TaskState.DELETED || this.state == TaskState.DELETED){
            throw new Exception("Can't change task state from " + this.state + " to " + state);
        }
        this.state = state;
    }

    public TaskState getState(){
        return this.state;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        return name.equals( ((Task) o).name);
    }

    @Override
    public int hashCode() {
        return this.name.hashCode();
    }
}
