package com.emobile.springtodo.core.service;

import com.emobile.springtodo.core.dao.Dao;
import com.emobile.springtodo.core.dto.input.TaskRequest;
import com.emobile.springtodo.core.dto.input.TaskUpdateRequest;
import com.emobile.springtodo.core.dto.output.TaskResponse;
import com.emobile.springtodo.core.entity.Task;
import com.emobile.springtodo.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

import static com.emobile.springtodo.core.mapper.TaskMapper.*;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class TaskServiceImpl implements TaskService {

    private final Dao<Task, Long> dao;

    @Override
    @Transactional(readOnly = true)
    @Cacheable(value = "TaskService::getById", key = "#id")
    public TaskResponse getById(Long id) {
        Task task = dao.findById(id)
                .orElseThrow(
                        () -> new NotFoundException("Task by id=%d not found"
                                .formatted(id)));

        log.info("Get task from database with id=%d".formatted(id));

        return fromEntityToResponse(task);
    }

    @Override
    @Transactional(readOnly = true)
    public List<TaskResponse> getAll() {
        log.info("Get all task from database...");

        return fromListEntityToListResponse((List<Task>) dao.findAll());
    }

    @Override
    @CachePut(value = "TaskService::getById", key = "#result.id")
    public TaskResponse create(TaskRequest request) {
        Task task = dao.save(formRequestToEntity(request));
        log.info("Saving task to database...");
        TaskResponse response = fromEntityToResponse(task);
        return response;
    }

    @Override
    @CacheEvict(value = "TaskService::getById", key = "#id")
    public void deleteById(Long id) {
        dao.findById(id)
                .orElseThrow(
                        () -> new NotFoundException("Task by id=%d not found"
                                .formatted(id)));


        dao.deleteById(id);

        log.info("Deleting task from database with id=%d".formatted(id));
    }

    @Override
    @CachePut(value = "TaskService::getById", key = "#request.id")
    public TaskResponse update(TaskUpdateRequest request) {
        Task task = dao.findById(request.id())
                .orElseThrow(
                        () -> new NotFoundException("Task by id=%d not found"
                                .formatted(request.id())));

        log.info("Get task from database with id=%d".formatted(task.getId()));

        task.setTitle(request.title());
        task.setDescription(task.getDescription());
        task.setUpdated(LocalDateTime.now());
        task.setStatus(request.status());

        dao.update(task);

        log.info("Task with id=%d updated to database".formatted(request.id()));
        return fromEntityToResponse(task);
    }
}