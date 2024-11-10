package com.emobile.springtodo.core.dao;

import com.emobile.springtodo.config.TestConfig;
import com.emobile.springtodo.core.entity.Status;
import com.emobile.springtodo.core.entity.Task;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static com.emobile.springtodo.util.TestData.*;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(classes = {TestConfig.class})
@Sql(scripts = "/sql/schema.sql")
@Transactional
class JdbcTaskDaoImplTest {

    @Autowired
    private Dao<Task, Long> dao;

    @Test
    void findById() {
        Long id = 10L;

        Task actual = dao.findById(id).get();

        assertThat(actual).isNotNull();
        assertThat(actual).isEqualTo(EXPECTED_TASK1);
    }

    @Test
    void findAll() {
        List<Task> tasks = new ArrayList<>(List.of(EXPECTED_TASK1, EXPECTED_TASK2));

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
        Long id = 10L;
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
    void deleteById() {
        Long id = 10L;

        dao.deleteById(id);

        List<Task> tasks = (List<Task>) dao.findAll();

        assertThat(tasks.size()).isEqualTo(1);
        assertThat(tasks).contains(EXPECTED_TASK2);
    }
}