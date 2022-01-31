package com.example.demo.entity;

import com.example.demo.exceptions.StoreException;

import java.util.Collection;

/**
 * Интрефейс для реализации класса хранения
 * @param <T>
 */
public interface Storable<T> {
    void save(T t) throws StoreException;
    void delete(T t) throws StoreException;
    T findByUserAndTask(String userName, String taskName) throws StoreException;
    Collection<T> get(String userName);
}
