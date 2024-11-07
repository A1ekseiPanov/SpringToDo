package com.emobile.springtodo.core.dao;

import java.util.Optional;

public interface Dao<E, ID> {
    Optional<E> findById(ID id);

    Iterable<E> findAll();

    E save(E entity);

    void update(E entity);

    void deleteById(ID id);
}