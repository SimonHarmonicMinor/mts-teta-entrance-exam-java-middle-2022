package com.example.demo.repo;

import java.util.List;

public interface RepositoryInterface<NAME, ENTITY> {
    void create(ENTITY entity);

    void remove(NAME name);

    ENTITY getEntity(NAME name);

    List<ENTITY> readAll();

    void close(NAME name);

    void reopen(NAME name);
}
