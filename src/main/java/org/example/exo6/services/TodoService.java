package org.example.exo6.services;

import org.example.exo6.entities.Todo;
import org.example.exo6.repositories.TodoRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class TodoService {
    private final TodoRepository todoRepository;
    public TodoService(TodoRepository todoRepository) {
        this.todoRepository = todoRepository;
    }

    public Todo save(Todo todo) {
        if(todo == null)
            return null;

        return todoRepository.save(todo);
    }

    public List<Todo> getAllTodo() {
        return new ArrayList<>(todoRepository.findAll());
    }

    public List<Todo> getAllValidTodo(){
        List<Todo> todos = new ArrayList<>(todoRepository.findAll());
        return todos.stream().filter(Todo::isValid).toList();

    }

    public List<Todo> getAllNotValidTodo(){
        List<Todo> todos = new ArrayList<>(todoRepository.findAll());
        return todos.stream().filter(todo -> !todo.isValid()).toList();
    }

    public Todo findById(UUID id) {
        if(id == null)
            return null;

        return todoRepository.findById(id).orElse(null);
    }

    public Todo delete(UUID id) {
        Todo todo = findById(id);
        if(todo == null)
            return null;

        todoRepository.delete(todo);
        return todo;
    }
}
