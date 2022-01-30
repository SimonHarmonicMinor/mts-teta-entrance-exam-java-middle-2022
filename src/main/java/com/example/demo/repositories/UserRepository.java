package com.example.demo.repositories;

import com.example.demo.Container;

public class UserRepository extends Repository {
    public String getUser() {
        return this.container.getUser();
    }

}
