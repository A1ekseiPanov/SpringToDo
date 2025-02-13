package com.emobile.springtodo.core.dao;

import com.emobile.springtodo.core.entity.Task;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class JdbcTaskDaoImpl implements Dao<Task, Long> {
    private static final String SQL_FIND_BY_ID = """
            SELECT id, title, description, status, created, updated  
            FROM dbo.task WHERE id = ?
            """;

    private static final String SQL_FIND_BY_TITLE = """
            SELECT id, title, description, status, created, updated  
            FROM dbo.task WHERE title = ?
            """;
    private static final String SQL_CREATE_TASK = """
            INSERT INTO dbo.task (title, description, status) 
            VALUES (?,?,?)
            """;
    private static final String SQL_FIND_ALL = """
            SELECT id, title, description, status, created, updated  
            FROM dbo.task
             """;

    private static final String SQL_DELETE = """
            DELETE FROM dbo.task WHERE id = ?
             """;

    private static final String SQL_UPDATE = """
            UPDATE dbo.task SET title = ?, description= ?, status= ?, updated = ? WHERE id = ?
             """;

    public static final String SQL_FIND_ALL_LIMIT_OFFSET = SQL_FIND_ALL + """
            LIMIT ?
            OFFSET ?
            """;

    private final JdbcTemplate jdbc;

    @Override
    public Optional<Task> findById(Long id) {
        return jdbc.query(SQL_FIND_BY_ID, rowMapper(), id)
                .stream()
                .findFirst();
    }

    @Override
    public Optional<Task> findByTitle(String title) {
        return jdbc.query(SQL_FIND_BY_TITLE, rowMapper(), title)
                .stream()
                .findFirst();
    }

    @Override
    public Iterable<Task> findAll() {
        return jdbc.query(SQL_FIND_ALL, rowMapper());
    }

    @Override
    public Iterable<Task> findAll(int limit, int offset) {
        return jdbc.query(SQL_FIND_ALL_LIMIT_OFFSET, rowMapper(), limit, offset);
    }

    @Override
    public Task save(Task entity) {
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbc.update(connection -> {
            PreparedStatement ps = connection
                    .prepareStatement(SQL_CREATE_TASK, new String[]{"id"});
            ps.setString(1, entity.getTitle());
            ps.setString(2, entity.getDescription());
            ps.setString(3, entity.getStatus());
            return ps;
        }, keyHolder);

        return jdbc.queryForObject(SQL_FIND_BY_ID, rowMapper(), keyHolder.getKey());
    }

    @Override
    public void deleteById(Long id) {
        jdbc.update(SQL_DELETE, id);
    }

    @Override
    public void update(Task entity) {
        jdbc.update(SQL_UPDATE,
                entity.getTitle(),
                entity.getDescription(),
                entity.getStatus(),
                entity.getUpdated(),
                entity.getId());
    }

    private RowMapper<Task> rowMapper() {
        return (ResultSet resultSet, int rowNum) -> Task.builder()
                .id(resultSet.getLong("id"))
                .title(resultSet.getString("title"))
                .description(resultSet.getString("description"))
                .status(resultSet.getString("status"))
                .created(resultSet.getTimestamp("created").toLocalDateTime())
                .updated(resultSet.getTimestamp("updated").toLocalDateTime())
                .build();
    }
}