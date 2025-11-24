package de.schaeferd.fullstackapp.todo;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class TodoRepository
{
    private final JdbcClient jdbcClient;

    List<Todo> getTodos()
    {
        return jdbcClient.sql("SELECT * FROM todo")
                .query(Todo.class)
                .list();
    }
}
