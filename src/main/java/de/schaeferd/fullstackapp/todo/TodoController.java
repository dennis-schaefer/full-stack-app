package de.schaeferd.fullstackapp.todo;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class TodoController
{
    private final TodoService todoService;

    @GetMapping("/api/v1/todos")
    public List<Todo> getTodos()
    {
        return todoService.getTodos();
    }
}
