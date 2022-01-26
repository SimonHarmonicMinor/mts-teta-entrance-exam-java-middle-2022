package com.example.demo.model;

import com.example.demo.enums.Result;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ResponseTest {

    @Test
    void test_getAsString() {
        Response r1 = new Response(Result.CREATED);
        assertEquals(Result.CREATED.name(), r1.getAsString());

        List<String> tasks = new ArrayList<>();
        tasks.add("wash hands");
        tasks.add("new task");
        tasks.add("one more task");

        Response r2 = new Response(Result.CREATED, tasks);
        assertEquals("CREATED [wash hands, new task, one more task]", r2.getAsString());
    }
}