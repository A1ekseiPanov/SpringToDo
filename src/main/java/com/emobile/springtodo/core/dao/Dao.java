package com.emobile.springtodo.core.dao;

import com.emobile.springtodo.core.entity.Task;

import java.util.Optional;

public interface Dao<E, ID> {
    Optional<E> findById(ID id);

    Optional<Task> findByTitle(String title);

    Iterable<E> findAll();

    E save(E entity);

    void update(E entity);

    void deleteById(ID id);
}