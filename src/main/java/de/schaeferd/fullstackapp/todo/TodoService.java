package de.schaeferd.fullstackapp.todo;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TodoService
{
    private final TodoRepository todoRepository;

    public List<Todo> getTodos()
    {
        return todoRepository.getTodos();
    }
}
