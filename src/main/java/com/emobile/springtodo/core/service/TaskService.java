package com.emobile.springtodo.core.service;

import com.emobile.springtodo.core.dto.input.TaskRequest;
import com.emobile.springtodo.core.dto.input.TaskUpdateRequest;
import com.emobile.springtodo.core.dto.output.TaskResponse;

import java.util.List;

public interface TaskService {
    TaskResponse getById(Long id);

    List<TaskResponse> getAll();

    List<TaskResponse> getAll(int pageNumber, int pageSize);

    TaskResponse create(TaskRequest request);

    void deleteById(Long id);

    TaskResponse update(TaskUpdateRequest request);
}