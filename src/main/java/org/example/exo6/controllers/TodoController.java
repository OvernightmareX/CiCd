package org.example.exo6.controllers;

import org.example.exo6.entities.Todo;
import org.example.exo6.services.TodoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/todo")
public class TodoController {
    private final TodoService todoService;

    public TodoController(TodoService todoService) {
        this.todoService = todoService;
    }

    @GetMapping
    public ResponseEntity<List<Todo>> getAllTodos() {
        return ResponseEntity.ok(todoService.getAllTodo());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Todo> getTodoById(@PathVariable UUID id) {
        Todo user = todoService.findById(id);
        if(user == null)
            return ResponseEntity.notFound().build();

        return ResponseEntity.ok(user);
    }

    @GetMapping("/isValid")
    public ResponseEntity<List<Todo>> getAllValidTodos() {
        return ResponseEntity.ok(todoService.getAllValidTodo());
    }

    @GetMapping("/isNotValid")
    public ResponseEntity<List<Todo>> getAllNotValidTodos() {
        return ResponseEntity.ok(todoService.getAllNotValidTodo());
    }

    @PostMapping
    public ResponseEntity<Todo> addUser(@RequestBody Todo todo) {
        return ResponseEntity.status(HttpStatus.CREATED).body(todoService.save(todo));
    }

    @PutMapping
    public ResponseEntity<Todo> updateUser(@RequestBody Todo todo) {
        return ResponseEntity.status(HttpStatus.OK).body(todoService.save(todo));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Todo> deleteUser(@PathVariable UUID id) {
        return ResponseEntity.ok(todoService.delete(id));
    }
}
