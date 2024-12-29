package com.emobile.springtodo.core.service;

import com.emobile.springtodo.core.dao.Dao;
import com.emobile.springtodo.core.dto.input.TaskRequest;
import com.emobile.springtodo.core.dto.output.TaskResponse;
import com.emobile.springtodo.core.entity.Task;
import com.emobile.springtodo.core.mapper.TaskMapper;
import com.emobile.springtodo.exception.NotFoundException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static com.emobile.springtodo.core.mapper.TaskMapper.fromEntityToResponse;
import static com.emobile.springtodo.util.TestData.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TaskServiceImplTest {
    @InjectMocks
    private TaskServiceImpl taskService;
    @Mock
    private Dao<Task, Long> dao;

    @Test
    void getById_Successfully() {
        when(dao.findById(EXPECTED_TASK1.getId()))
                .thenReturn(Optional.of(EXPECTED_TASK1));

        TaskResponse byId = taskService.getById(EXPECTED_TASK1.getId());

        Assertions.assertThat(byId).isEqualTo(fromEntityToResponse(EXPECTED_TASK1));
    }

    @Test
    void getById_IdNotFound_ThrowNotFoundException() {
        Long id = 999L;

        when(dao.findById(id))
                .thenReturn(Optional.empty());

        assertThatThrownBy(() -> taskService.getById(id))
                .isInstanceOf(NotFoundException.class)
                .hasMessage("Task by id=%d not found"
                        .formatted(id));
    }


    @Test
    void getAll_Successfully() {
        List<Task> tasks = List.of(EXPECTED_TASK1, EXPECTED_TASK2);
        when(dao.findAll()).thenReturn(tasks);

        List<TaskResponse> actual = taskService.getAll();

        assertThat(actual).isNotEmpty();
        assertThat(actual).isEqualTo(TaskMapper.fromListEntityToListResponse(tasks));

    }

    @Test
    void getAll_Pagination_Successfully() {
        int pageNumber = 1;
        int pageSize = 1;
        int offset = (pageNumber - 1) * pageSize;
        List<Task> tasks = List.of(EXPECTED_TASK1);

        when(dao.findAll(pageSize, offset)).thenReturn(tasks);

        List<TaskResponse> actual = taskService.getAll(pageNumber, pageSize);

        assertThat(actual).isNotEmpty();
        assertThat(actual).isEqualTo(TaskMapper.fromListEntityToListResponse(tasks));
    }


    @Test
    void create_Successfully() {
        TaskRequest request = TaskRequest.builder()
                .title(EXPECTED_TASK1.getTitle())
                .description(EXPECTED_TASK1.getDescription())
                .build();

        when(dao.save(Mockito.any(Task.class))).thenReturn(EXPECTED_TASK1);

        TaskResponse response = taskService.create(request);

        assertThat(response).isNotNull();
        assertThat(response.title()).isEqualTo(EXPECTED_TASK1.getTitle());
        assertThat(response.description()).isEqualTo(EXPECTED_TASK1.getDescription());
        assertThat(response.status()).isEqualTo(EXPECTED_TASK1.getStatus());


    }

    @Test
    void deleteById_Successfully() {
        Long id = 1L;

        when(dao.findById(id)).thenReturn(Optional.of(Task.builder().id(id).build()));

        taskService.deleteById(id);


        verify(dao).deleteById(id);

    }

    @Test
    void deleteById_IdNotFound_ThrowNotFoundException() {
        Long id = 1L;

        when(dao.findById(id)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> taskService.deleteById(id))
                .isInstanceOf(NotFoundException.class)
                .hasMessage("Task by id=%d not found"
                        .formatted(id));
    }

    @Test
    void update_Successfully() {
        when(dao.findById(UPDATE_REQUEST.id())).thenReturn(Optional.of(EXPECTED_TASK1));

        TaskResponse update = taskService.update(UPDATE_REQUEST);


        assertThat(update.id()).isEqualTo(UPDATE_REQUEST.id());
        assertThat(update.title()).isEqualTo(UPDATE_REQUEST.title());
        assertThat(update.description()).isEqualTo(UPDATE_REQUEST.description());
        assertThat(update.status()).isEqualTo(UPDATE_REQUEST.status());
    }

    @Test
    void update_IdNotFound_ThrowNotFoundException() {
        when(dao.findById(UPDATE_REQUEST.id())).thenReturn(Optional.empty());

        assertThatThrownBy(() -> taskService.update(UPDATE_REQUEST))
                .isInstanceOf(NotFoundException.class)
                .hasMessage("Task by id=%d not found"
                        .formatted(UPDATE_REQUEST.id()));
    }
}