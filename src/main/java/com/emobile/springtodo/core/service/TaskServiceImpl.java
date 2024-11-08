package com.emobile.springtodo.core.service;

import com.emobile.springtodo.core.dao.Dao;
import com.emobile.springtodo.core.entity.Task;
import com.emobile.springtodo.core.mapper.TaskMapper;
import com.emobile.springtodo.dto.input.TaskRequest;
import com.emobile.springtodo.dto.input.TaskUpdateRequest;
import com.emobile.springtodo.dto.output.TaskResponse;
import com.emobile.springtodo.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

import static com.emobile.springtodo.core.mapper.TaskMapper.fromEntityToResponse;
import static com.emobile.springtodo.core.mapper.TaskMapper.fromListEntityToListResponse;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class TaskServiceImpl implements TaskService {

    private final Dao<Task, Long> dao;

    @Override
    @Transactional(readOnly = true)
    public TaskResponse getById(Long id) {
        TaskResponse response = fromEntityToResponse(dao.findById(id)
                .orElseThrow(
                        () -> new NotFoundException("Task by id=%s not found"
                                .formatted(id))));

        log.info("Get task from database with id-%d".formatted(id));

        return response;
    }

    @Override
    @Transactional(readOnly = true)
    public List<TaskResponse> getAll() {
        log.info("Get all task from database...");

        return fromListEntityToListResponse((List<Task>) dao.findAll());
    }

    @Override
    public TaskResponse create(TaskRequest request) {
        log.info("Saving task to database...");

        return fromEntityToResponse(dao.save(TaskMapper.formRequestToEntity(request)));
    }

    @Override
    public void deleteById(Long id) {
        dao.findById(id)
                .orElseThrow(
                        () -> new NotFoundException("Task by id=%s not found"
                                .formatted(id)));


        dao.deleteById(id);

        log.info("Deleting task from database with id-%d".formatted(id));
    }

    @Override
    public void update(Long id, TaskUpdateRequest request) {
        Task task = dao.findById(id)
                .orElseThrow(
                        () -> new NotFoundException("Task by id=%s not found"
                                .formatted(id)));

        log.info("Get task from database with id-%d".formatted(task.getId()));

        task.setTitle(request.title());
        task.setDescription(task.getDescription());
        task.setUpdated(LocalDateTime.now());
        task.setStatus(request.status());

        dao.update(task);

        log.info("Task with id-%d updated to database".formatted(id));
    }
}