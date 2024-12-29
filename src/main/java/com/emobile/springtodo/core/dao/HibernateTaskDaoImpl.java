package com.emobile.springtodo.core.dao;

import com.emobile.springtodo.core.entity.Task;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class HibernateTaskDaoImpl implements Dao<Task, Long> {

    @PersistenceContext
    private EntityManager em;

    @Override
    public Optional<Task> findById(Long id) {
        return Optional.ofNullable(em.find(Task.class, id));
    }

    @Override
    public Optional<Task> findByTitle(String title) {
        List<Task> result = em
                .createQuery("select t from Task t where t.title = :title", Task.class)
                .setParameter("title", title)
                .getResultList();
        return result.isEmpty() ? Optional.empty() : Optional.of(result.get(0));
    }

    @Override
    public Iterable<Task> findAll() {
        return em.createQuery(
                        "select t from Task t", Task.class)
                .getResultList();
    }

    @Override
    public Task save(Task entity) {
        em.persist(entity);
        return entity;
    }

    @Override
    public void update(Task entity) {
        em.merge(entity);
        em.flush();
    }

    @Override
    public void deleteById(Long id) {
        em.remove(em.find(Task.class, id));
    }

    @Override
    public Iterable<Task> findAll(int limit, int offset) {
        String query = "select t from Task t";
        return em.createQuery(query, Task.class)
                .setFirstResult(offset)
                .setMaxResults(limit)
                .getResultList();
    }
}