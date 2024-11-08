package com.emobile.springtodo.core.service;

import com.emobile.springtodo.core.entity.Task;
import com.emobile.springtodo.dto.input.TaskRequest;
import com.emobile.springtodo.dto.input.TaskUpdateRequest;
import com.emobile.springtodo.dto.output.TaskResponse;

import java.util.List;

public interface TaskService {
    TaskResponse getById(Long id);
    List<TaskResponse> getAll();
    TaskResponse create(TaskRequest request);
    void deleteById(Long id);
    void update(Long id, TaskUpdateRequest request);
}