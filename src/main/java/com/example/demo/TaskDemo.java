package com.example.demo;

import java.util.ArrayList;
import java.util.List;

public class TaskDemo {
    private String name;
    private String status;
    private String avtor;
    private List<String> statusList;

    public TaskDemo(String name, String avtor){
        this.name = name;
        this.avtor = avtor;
        this.status = "CREATED";
        statusList= new ArrayList<String>();
        statusList.add("CREATED");
        statusList.add("CLOSED");
        statusList.add("DELETED");
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getAvtor() {
        return avtor;
    }

    public void setAvtor(String avtor) {
        this.avtor = avtor;
    }
    public String statusNew(String avtor, String status){
        if(this.avtor.equals(avtor)) {
            if (statusList.contains(status)) {
                switch (status) {
                    case "CREATED":
                        if (this.status.equals("CLOSED")) {
                            this.status = "CREATED";
                        }
                        break;
                    case "CLOSED":
                        if (this.status.equals("CREATED")) {
                            this.status = "CLOSED";
                        }
                        break;
                    case "DELETED":
                        if (this.status.equals("CLOSED")) {
                            this.status = "DELETED";
                        }
                        break;
                }
                return this.status;
            } else {
                return "ERROR Unknown status";
            }
        }else{
            return "ACCESS_DENIED";
        }
    }

    public String toString() {
        return "{name: " + name + ",status: " + status + "}";
    }
}
