package com.emobile.springtodo.util;

import com.emobile.springtodo.core.dto.input.TaskUpdateRequest;
import com.emobile.springtodo.core.entity.Status;
import com.emobile.springtodo.core.entity.Task;

import java.time.LocalDateTime;

public final class TestData {
    private TestData() {
    }

    public static final Task EXPECTED_TASK1 = Task.builder()
            .id(10L)
            .title("title1")
            .description("description1")
            .status(Status.CREATED.toString())
            .created(LocalDateTime.of(2024, 04, 14, 14, 44, 44))
            .updated(LocalDateTime.of(2024, 04, 14, 14, 44, 44))
            .build();
    public static final Task EXPECTED_TASK1_DUPLICATE = Task.builder()
            .title("title1")
            .description("description1")
            .build();

    public static final Task EXPECTED_TASK2 = Task.builder()
            .id(11L)
            .title("title2")
            .description("description2")
            .status(Status.CREATED.toString())
            .created(LocalDateTime.of(2024, 04, 14, 14, 44, 44))
            .updated(LocalDateTime.of(2024, 04, 14, 14, 44, 44))
            .build();

    public static final Task NEW_TASK = Task.builder()
            .title("Задача №1")
            .description("Описание задачи №1")
            .status(Status.CREATED.toString())
            .build();

    public static final Task UPDATED_TASK = Task.builder()
            .id(10L)
            .title("Задача №1 обновленная")
            .description("Описание задачи №1 обновленное")
            .status(Status.DO_WORK.toString())
            .updated(LocalDateTime.of(2024, 12, 12, 12, 12, 12))
            .build();

    public static final TaskUpdateRequest UPDATE_REQUEST = TaskUpdateRequest.builder()
            .id(10L)
            .title("Update Title")
            .description("Update Description")
            .status(Status.DO_WORK.toString())
            .build();

    public static final TaskUpdateRequest UPDATE_REQUEST_NOT_FOUND = TaskUpdateRequest.builder()
            .id(9999L)
            .title("Update Title")
            .description("Update Description")
            .status(Status.DO_WORK.toString())
            .build();
}