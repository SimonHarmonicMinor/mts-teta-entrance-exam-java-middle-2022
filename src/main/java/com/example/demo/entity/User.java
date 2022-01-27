package com.example.demo.entity;

import java.util.ArrayList;
import java.util.List;

public class User {
    final String name;

    public User(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object obj) {
        return getName().equals(((User)obj).getName());
    }
}
