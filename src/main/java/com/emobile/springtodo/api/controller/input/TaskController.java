package com.emobile.springtodo.api.controller.input;

import com.emobile.springtodo.api.swagger.ITaskController;
import com.emobile.springtodo.core.dto.input.TaskRequest;
import com.emobile.springtodo.core.dto.input.TaskUpdateRequest;
import com.emobile.springtodo.core.dto.output.TaskResponse;
import com.emobile.springtodo.core.service.TaskService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/api/tasks/", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class TaskController implements ITaskController {
    private final TaskService taskService;

    @Override
    @GetMapping("/{id}")
    public ResponseEntity<TaskResponse> getTaskById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(taskService.getById(id));
    }

    @Override
    @GetMapping
    public ResponseEntity<List<TaskResponse>> getAllTasks() {
        return ResponseEntity.ok(taskService.getAll());
    }

    @Override
    @PostMapping
    public ResponseEntity<TaskResponse> saveTask(@Valid @RequestBody TaskRequest request,
                                                 UriComponentsBuilder uriComponentsBuilder) {
        TaskResponse response = taskService.create(request);
        return ResponseEntity.created(uriComponentsBuilder
                        .replacePath("/api/tasks/{taskId}")
                        .build(Map.of("taskId", response.id())))
                .body(response);
    }

    @Override
    @PutMapping
    public ResponseEntity<?> updateTask(@Valid @RequestBody TaskUpdateRequest request) {
        taskService.update(request);
        return ResponseEntity.noContent().build();
    }

    @Override
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteTask(@PathVariable("id") Long id) {
        taskService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}