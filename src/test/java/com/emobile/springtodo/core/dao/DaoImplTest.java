package com.emobile.springtodo.core.dao;

import com.emobile.springtodo.core.entity.Status;
import com.emobile.springtodo.core.entity.Task;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
@Sql("/sql/schema.sql")
class TaskJdbcTaskDaoImplTest {
    private final static Task EXPECTED_1 = Task.builder()
            .id(1L)
            .title("title1")
            .description("description1")
            .status(Status.CREATED.toString())
            .created(LocalDateTime.of(2024, 04, 14, 14, 44, 44))
            .updated(LocalDateTime.of(2024, 04, 14, 14, 44, 44))
            .build();

    private final static Task EXPECTED_2 = Task.builder()
            .id(2L)
            .title("title2")
            .description("description2")
            .status(Status.CREATED.toString())
            .created(LocalDateTime.of(2024, 04, 14, 14, 44, 44))
            .updated(LocalDateTime.of(2024, 04, 14, 14, 44, 44))
            .build();

    private static final Task NEW_TASK = Task.builder()
            .title("Задача №1")
            .description("Описание задачи №1")
            .status(Status.CREATED.toString())
            .build();

    private static final Task UPDATED_TASK = Task.builder()
            .id(1L)
            .title("Задача №1 обновленная")
            .description("Описание задачи №1 обновленное")
            .status(Status.DO_WORK.toString())
            .updated(LocalDateTime.of(2024, 12, 12, 12, 12, 12))
            .build();

    @Autowired
    private Dao<Task, Long> dao;

    @Test
    void findById() {
        Long id = 1L;

        Task actual = dao.findById(id).get();

        assertThat(actual).isNotNull();
        assertThat(actual).isEqualTo(EXPECTED_1);
    }

    @Test
    void findAll() {
        List<Task> tasks = new ArrayList<>(List.of(EXPECTED_1, EXPECTED_2));

        Iterable<Task> all = dao.findAll();

        assertThat(all).isNotNull();
        assertThat(all).isEqualTo(tasks);
    }

    @Test
    void save() {
        Task actual = dao.save(NEW_TASK);

        assertThat(actual.getTitle()).isEqualTo(NEW_TASK.getTitle());
        assertThat(actual.getDescription()).isEqualTo(NEW_TASK.getDescription());
        assertThat(actual.getStatus()).isEqualTo(NEW_TASK.getStatus());
    }


    @Test
    void update() {
        Long id = 1L;
        Task expected = Task.builder()
                .id(id)
                .title("Задача №1 обновленная")
                .description("Описание задачи №1 обновленное")
                .status(Status.DO_WORK.toString())
                .created(LocalDateTime.of(2024, 04, 14, 14, 44, 44))
                .updated(LocalDateTime.of(2024, 12, 12, 12, 12, 12))
                .build();

        dao.update(UPDATED_TASK);

        assertThat(dao.findById(id).get()).isEqualTo(expected);
    }

    @Test
    void deleteById() throws SQLException {
        Long id = 1L;

        dao.deleteById(id);

        List<Task> tasks = (List<Task>) dao.findAll();

        assertThat(tasks.size()).isEqualTo(1);
        assertThat(tasks).contains(EXPECTED_2);
    }
}