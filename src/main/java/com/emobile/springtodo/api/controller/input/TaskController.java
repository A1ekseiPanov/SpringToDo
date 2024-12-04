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

import static com.emobile.springtodo.api.controller.input.TaskController.*;

@RestController
@RequestMapping(value = PATH_TASK, produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class TaskController implements ITaskController {
    public static final String PATH_TASK = "/api/tasks/";
    private final TaskService taskService;

    @Override
    @GetMapping("/{id}")
    public ResponseEntity<TaskResponse> getTaskById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(taskService.getById(id));
    }

    @Override
    @GetMapping
    public ResponseEntity<List<TaskResponse>> getAllTasks(
            @RequestParam(value = "page_number", required = false, defaultValue = "1") Integer pageNumber,
            @RequestParam(value = "page_size", required = false, defaultValue = "20") Integer pageSize) {
        return ResponseEntity.ok(taskService.getAll(pageNumber, pageSize));
    }

    @Override
    @PostMapping
    public ResponseEntity<TaskResponse> saveTask(@Valid @RequestBody TaskRequest request,
                                                 UriComponentsBuilder uriComponentsBuilder) {
        TaskResponse response = taskService.create(request);
        return ResponseEntity.created(uriComponentsBuilder
                        .replacePath(PATH_TASK + "{taskId}")
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