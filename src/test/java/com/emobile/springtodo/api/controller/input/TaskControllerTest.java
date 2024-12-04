package com.emobile.springtodo.api.controller.input;

import com.emobile.springtodo.config.TestConfig;
import com.emobile.springtodo.core.entity.Task;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.emobile.springtodo.api.controller.input.TaskController.PATH_TASK;
import static com.emobile.springtodo.util.TestData.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(classes = {TestConfig.class})
@AutoConfigureMockMvc(printOnlyOnFailure = false)
@Sql(value = "/sql/schema.sql")
@Transactional
class TaskControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void getTaskById_Successfully() throws Exception {
        Long id = 11L;
        mockMvc.perform(get(PATH_TASK + "/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpectAll(
                        status().isOk(),
                        content().contentType(MediaType.APPLICATION_JSON),
                        content().json(objectMapper.writeValueAsString(EXPECTED_TASK2))
                );
    }

    @Test
    void getTaskById_IdNotFound_ThrowNotFoundException() throws Exception {
        Long id = 9999L;
        mockMvc.perform(get(PATH_TASK + "/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpectAll(
                        status().isNotFound(),
                        content().contentType(MediaType.APPLICATION_PROBLEM_JSON),
                        content().json("""
                                {
                                  "type": "about:blank",
                                  "title": "Not Found",
                                  "status": 404,
                                  "detail": "Task by id=9999 not found",
                                  "instance": "/api/tasks/9999"
                                }

                                """)
                );
    }

    @Test
    void getAllTasks_Successfully() throws Exception {
        mockMvc.perform(get(PATH_TASK)
                        .param("page_number", "2")
                        .param("page_size", "1"))
                .andExpectAll(
                        status().isOk(),
                        content().contentType(MediaType.APPLICATION_JSON),
                        content().json(objectMapper.writeValueAsString(List.of(EXPECTED_TASK2)))
                );
    }

    @Test
    void saveTask_Successfully() throws Exception {
        mockMvc.perform(post(PATH_TASK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(NEW_TASK)))
                .andExpectAll(
                        status().isCreated(),
                        header().string(HttpHeaders.LOCATION, "http://localhost" + PATH_TASK + "1"),
                        content().contentType(MediaType.APPLICATION_JSON),
                        jsonPath("$.id").isNotEmpty(),
                        jsonPath("$.title").value("Задача №1"),
                        jsonPath("$.description").value("Описание задачи №1"),
                        jsonPath("$.status").value("CREATED"),
                        jsonPath("$.created").isNotEmpty(),
                        jsonPath("$.updated").isNotEmpty());
    }

    @Test
    void saveTask_IncorrectData_ValidationError() throws Exception {
        mockMvc.perform(post(PATH_TASK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(new Task())))
                .andExpectAll(
                        status().isBadRequest(),
                        content().contentType(MediaType.APPLICATION_PROBLEM_JSON),
                        content().json("""
                                {
                                  "type": "about:blank",
                                  "title": "Bad Request",
                                  "status": 400,
                                  "detail": "validation error",
                                  "instance": "/api/tasks/",
                                  "errors": [
                                    "title should not be empty",
                                    "description should not be empty"
                                  ]
                                }
                                """));
    }

    @Test
    void saveTask_TitleExist_ValidationError() throws Exception {
        mockMvc.perform(post(PATH_TASK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(EXPECTED_TASK1_DUPLICATE)))
                .andExpectAll(
                        status().isBadRequest(),
                        content().contentType(MediaType.APPLICATION_PROBLEM_JSON),
                        content().json("""
                                {
                                   "type": "about:blank",
                                   "title": "Bad Request",
                                   "status": 400,
                                   "detail": "validation error",
                                   "instance": "/api/tasks/",
                                   "errors": [
                                     "task with same title already exists"
                                   ]
                                 }
                                """));
    }

    @Test
    void updateTask_Successfully() throws Exception {
        mockMvc.perform(put(PATH_TASK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(UPDATE_REQUEST)))
                .andExpect(status().isNoContent());
    }

    @Test
    void updateTask_IdNotFound_ThrowNotFoundException() throws Exception {
        mockMvc.perform(put(PATH_TASK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(UPDATE_REQUEST_NOT_FOUND)))
                .andExpectAll(
                        status().isNotFound(),
                        content().contentType(MediaType.APPLICATION_PROBLEM_JSON),
                        content().json("""
                                                                        
                                {
                                    "type": "about:blank",
                                    "title": "Not Found",
                                    "status": 404,
                                    "detail": "Task by id=9999 not found",
                                    "instance": "/api/tasks/"
                                }
                                                                        
                                                        
                                """));
    }

    @Test
    void deleteTask_Successfully() throws Exception {
        mockMvc.perform(delete(PATH_TASK + "/{id}", EXPECTED_TASK1.getId()))
                .andExpect(status().isNoContent());
    }

    @Test
    void deleteTask_IdNotFound_ThrowNotFoundException() throws Exception {
        Long notFoundId = 99999L;
        mockMvc.perform(delete(PATH_TASK + "/{id}", notFoundId))
                .andExpectAll(
                        status().isNotFound(),
                        content().contentType(MediaType.APPLICATION_PROBLEM_JSON),
                        content().json("""
                                {
                                  "type": "about:blank",
                                  "title": "Not Found",
                                  "status": 404,
                                  "detail": "Task by id=99999 not found",
                                  "instance": "/api/tasks/99999"
                                }
                                """)
                );
    }
}