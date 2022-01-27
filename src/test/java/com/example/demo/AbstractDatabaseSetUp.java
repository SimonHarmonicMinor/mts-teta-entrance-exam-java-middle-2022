package com.example.demo;

import com.example.demo.database.IDatabase;
import com.example.demo.database.InMemoryDatabase;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mockito;

public class AbstractDatabaseSetUp {

    protected static final String UNKNOWN = "unknown";
    protected IDatabase db;
    protected IDatabase spyDb;

    @BeforeEach
    public void prepareDatabase(){
        db = new InMemoryDatabase();

        db.addUser("user1");
        db.addUser("user2");
        db.addTask("user1", "task1");
        db.addTask("user1", "task2");
        db.addTask("user1", "task3");
        db.addTask("user2", "task4");
        db.addTask("user2", "task5");
        db.addTask("user2", "task6");

        spyDb = Mockito.spy(db);
    }
}
