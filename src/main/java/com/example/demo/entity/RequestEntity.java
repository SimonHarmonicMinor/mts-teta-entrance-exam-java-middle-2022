package com.example.demo.entity;

import java.util.Objects;

public class RequestEntity {

    private String userName;
    private String command;
    private String name;

    public RequestEntity() {
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getCommand() {
        return command;
    }

    public void setCommand(String command) {
        this.command = command;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RequestEntity that = (RequestEntity) o;
        return Objects.equals(userName, that.userName) &&
                Objects.equals(command, that.command) &&
                Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userName, command, name);
    }

    @Override
    public String toString() {
        return "RequestEntity{" +
                "userName='" + userName + '\'' +
                ", command='" + command + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
