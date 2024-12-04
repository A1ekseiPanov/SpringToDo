package com.emobile.springtodo.api.swagger;

import com.emobile.springtodo.core.dto.input.TaskRequest;
import com.emobile.springtodo.core.dto.input.TaskUpdateRequest;
import com.emobile.springtodo.core.dto.output.TaskResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@Tag(name = "Task controller", description = "Контроллер задач")
public interface ITaskController {
    @Operation(
            summary = "Получение задачи",

            description = "Получение задачи по id",
            responses = {
                    @ApiResponse(
                            description = "Задача найдеа",
                            responseCode = "200",
                            content = @Content(
                                    mediaType = "application/json",
                                    examples = {
                                            @ExampleObject(
                                                    value = """
                                                            {
                                                                "id": 1,
                                                                "title": "Task",
                                                                "description": "Description task!",
                                                                "status": "CREATED",
                                                                "created": "2024-11-09T12:49:50.761571",
                                                                "updated": "2024-11-09T12:49:50.761571"
                                                            }
                                                            """
                                            )
                                    }

                            )),
                    @ApiResponse(
                            description = "Задача не найдена",
                            responseCode = "404",
                            content = @Content(
                                    mediaType = "application/json",
                                    examples = {
                                            @ExampleObject(
                                                    value = """
                                                            {
                                                                "type": "about:blank",
                                                                "title": "Not Found",
                                                                "status": 404,
                                                                "detail": "Task by id=1 not found",
                                                                "instance": "/api/tasks/1"
                                                            }
                                                            """
                                            )
                                    }
                            ))
            }
    )
    ResponseEntity<TaskResponse> getTaskById(Long id);

    @Operation(
            summary = "Получение всех задач",
            description = "Получение задач",
            responses = {
                    @ApiResponse(
                            description = "Задачи найдены",
                            responseCode = "200",
                            content = @Content(
                                    mediaType = "application/json",
                                    examples = {
                                            @ExampleObject(
                                                    value = """
                                                            [
                                                            {
                                                                "id": 1,
                                                                "title": "Task1",
                                                                "description": "Description task1!",
                                                                "status": "CREATED",
                                                                "created": "2024-11-09T12:49:50.761571",
                                                                "updated": "2024-11-09T12:49:50.761571"
                                                            },
                                                            {
                                                                "id":2,
                                                                "title": "Task2",
                                                                "description": "Description task2!",
                                                                "status": "CREATED",
                                                                "created": "2024-11-09T12:49:50.761571",
                                                                "updated": "2024-11-09T12:49:50.761571"
                                                            }
                                                            ]
                                                            """
                                            )
                                    }
                            ))
            }
    )
    ResponseEntity<List<TaskResponse>> getAllTasks(Integer pageNumber,
                                                   Integer pageSize);

    @Operation(
            summary = "Сохранение задачи",
            description = "Сохраняет задачу",
            responses = {
                    @ApiResponse(
                            description = "Задача сохранена",
                            responseCode = "201",
                            content = @Content(
                                    mediaType = "application/json",

                                    examples = {
                                            @ExampleObject(
                                                    value = """
                                                            {
                                                                "id": 1,
                                                                "title": "Task1",
                                                                "description": "Description task1!",
                                                                "status": "CREATED",
                                                                "created": "2024-11-09T12:49:50.761571",
                                                                "updated": "2024-11-09T12:49:50.761571"
                                                            }
                                                            """
                                            )
                                    }
                            )
                    )
            }
    )
    ResponseEntity<TaskResponse> saveTask(TaskRequest request,
                                          UriComponentsBuilder uriComponentsBuilder);

    @Operation(
            summary = "Обновление задачи",
            description = "Обновление задачи по id",
            responses = {
                    @ApiResponse(
                            description = "Задача обновлена",
                            responseCode = "204"
                    ),
                    @ApiResponse(
                            description = "Задача не найдена",
                            responseCode = "404",
                            content = @Content(
                                    mediaType = "application/json",
                                    examples = {
                                            @ExampleObject(
                                                    value = """
                                                            {
                                                                "type": "about:blank",
                                                                "title": "Not Found",
                                                                "status": 404,
                                                                "detail": "Task by id=1 not found",
                                                                "instance": "/api/tasks/"
                                                            }
                                                            """
                                            )
                                    }
                            ))
            }
    )
    ResponseEntity<?> updateTask(TaskUpdateRequest request);

    @Operation(
            summary = "Удаление задачи",
            description = "Удаление задачи по id",
            responses = {
                    @ApiResponse(
                            description = "Задача удалена",
                            responseCode = "204"
                    ),
                    @ApiResponse(
                            description = "Задача не найдена",
                            responseCode = "404",
                            content = @Content(
                                    mediaType = "application/json",
                                    examples = {
                                            @ExampleObject(
                                                    value = """
                                                            {
                                                                "type": "about:blank",
                                                                "title": "Not Found",
                                                                "status": 404,
                                                                "detail": "Task by id=1 not found",
                                                                "instance": "/api/tasks/1"
                                                            }
                                                            """
                                            )
                                    }
                            ))
            }
    )
    ResponseEntity<?> deleteTask(Long id);
}