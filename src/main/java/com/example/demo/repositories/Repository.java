package com.example.demo.repositories;

import com.example.demo.Container;

public class Repository {
    protected final Container container;

    public Repository() {
        this.container = Container.getInstance();
    }
}
