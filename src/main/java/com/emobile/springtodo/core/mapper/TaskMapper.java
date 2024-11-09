package com.emobile.springtodo.core.mapper;

import com.emobile.springtodo.core.dto.input.TaskRequest;
import com.emobile.springtodo.core.dto.output.TaskResponse;
import com.emobile.springtodo.core.entity.Task;

import java.util.List;

public final class TaskMapper {
    private TaskMapper() {
    }

    public static TaskResponse fromEntityToResponse(Task task) {
        return TaskResponse.builder()
                .id(task.getId())
                .title(task.getTitle())
                .description(task.getDescription())
                .status(task.getStatus())
                .created(task.getCreated())
                .updated(task.getUpdated())
                .build();
    }

    public static List<TaskResponse> fromListEntityToListResponse(List<Task> task) {
        return task.stream().map(TaskMapper::fromEntityToResponse).toList();
    }

    public static Task formRequestToEntity(TaskRequest taskRequest) {
        return Task.builder()
                .title(taskRequest.title())
                .description(taskRequest.description())
                .build();
    }
}