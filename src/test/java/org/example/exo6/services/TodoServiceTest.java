package org.example.exo6.services;

import org.example.exo6.entities.Todo;
import org.example.exo6.repositories.TodoRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@SpringBootTest
public class TodoServiceTest {

    @Autowired
    private TodoRepository todoRepository;

    @Autowired
    private TodoService todoService;

    private List<Todo> todos = new ArrayList<>();

    @BeforeEach
    public void setUp() {
        initDB();
    }

    @Test
    void testSave_WhenTodoIsValid_HaveSavedTodo() {
        Todo newTodo = getTestTodo();
        Todo savedTodo = todoService.save(newTodo);

        Assertions.assertNotNull(savedTodo);
        Assertions.assertEquals(newTodo, savedTodo);
    }

    @Test
    void testSave_WhenTodoIsValid_HaveSavedTodoInDB() {
        Todo newTodo = getTestTodo();
        newTodo = todoService.save(newTodo);
        Todo savedTodo = todoService.findById(newTodo.getId());

        List<Todo> todoList = (List<Todo>) todoRepository.findAll();
        int dbSize = todoList.size();

        Assertions.assertNotNull(savedTodo);
        Assertions.assertEquals(todos.size()+1, dbSize);
        Assertions.assertEquals(newTodo, savedTodo);
    }

    @Test
    void testSave_WhenTodoIsNull_HaveNull_AndNoAdditionalValueInDB() {
        Todo newTodo = todoService.save(null);

        List<Todo> todoList = (List<Todo>) todoRepository.findAll();
        int dbSize = todoList.size();

        Assertions.assertNull(newTodo);
        Assertions.assertEquals(todos.size(), dbSize);
    }

    @Test
    void testGetAllTodo_HaveAllElementsFromListInDB() {
        List<Todo> retrievedTodos = todoService.getAllTodo();

        Assertions.assertNotNull(retrievedTodos);
        Assertions.assertEquals(todos.size(), retrievedTodos.size());
        Assertions.assertTrue(retrievedTodos.containsAll(todos));
    }

    @Test
    void testGetAllTodo_HaveEmptyList_WhenDBIsEmpty() {
        todoRepository.deleteAll();

        List<Todo> retrievedTodos = todoService.getAllTodo();

        Assertions.assertNotNull(retrievedTodos);
        Assertions.assertTrue(retrievedTodos.isEmpty());
    }


    @Test
    void testGetAllValidTodo_HaveOnlyValidTodo() {
        List<Todo> validTodos = todos.stream().filter(Todo::isValid).toList();
        List<Todo> validTodosInDb = todoService.getAllValidTodo();

        Assertions.assertNotNull(validTodos);
        Assertions.assertEquals(validTodos.size(), validTodosInDb.size());
        Assertions.assertTrue(validTodos.containsAll(validTodosInDb));
    }

    @Test
    void testGetAllValidTodo_HaveEmptyValidTodoList_WhenDBHaveNoValidTodo() {
        List<Todo> validTodos = todos.stream().filter(Todo::isValid).toList();
        todoRepository.deleteAll(validTodos);

        List<Todo> validTodosInDb = todoService.getAllValidTodo();

        Assertions.assertNotNull(validTodos);
        Assertions.assertTrue(validTodosInDb.isEmpty());
    }

    @Test
    void testGetAllNotValidTodo_HaveOnlyNotValidTodo() {
        List<Todo> notValidTodos = todos.stream().filter(todo -> !todo.isValid()).toList();
        List<Todo> notValidTodosInDb = todoService.getAllNotValidTodo();

        Assertions.assertNotNull(notValidTodos);
        Assertions.assertEquals(notValidTodos.size(), notValidTodosInDb.size());
        Assertions.assertTrue(notValidTodos.containsAll(notValidTodosInDb));
    }

    @Test
    void testGetAllNotValidTodo_HaveEmptyValidTodoList_WhenDBHaveNoValidTodo() {
        List<Todo> notValidTodos = todos.stream().filter(todo -> !todo.isValid()).toList();
        todoRepository.deleteAll(notValidTodos);

        List<Todo> notValidTodosInDb = todoService.getAllNotValidTodo();

        Assertions.assertNotNull(notValidTodos);
        Assertions.assertTrue(notValidTodosInDb.isEmpty());
    }

    @Test
    void testFindById() {
        Todo searchedTodo = todoService.findById(todos.get(0).getId());
        Assertions.assertNotNull(searchedTodo);
        Assertions.assertEquals(todos.get(0), searchedTodo);
    }

    @Test
    void testFindByIdNotFound() {
        Todo searchedTodo = todoService.findById(UUID.randomUUID());
        Assertions.assertNull(searchedTodo);
    }

    @Test
    void testFindById_WhenIdIsNull_HaveNull() {
        Todo searchedTodo = todoService.findById(null);
        Assertions.assertNull(searchedTodo);
    }

    @Test
    void testDelete_WhenTodoExist() {
        Todo deletedTodo = todoService.delete(todos.get(0).getId());

        Assertions.assertNotNull(deletedTodo);
        Assertions.assertEquals(deletedTodo, todos.get(0));
        Assertions.assertEquals(todos.size()-1, todoService.getAllTodo().size());
        Assertions.assertNull(todoService.findById(deletedTodo.getId()));
    }

    @Test
    void testDelete_WhenTodoNotExist() {
        Todo deletedTodo = todoService.delete(UUID.randomUUID());
        Assertions.assertNull(deletedTodo);
    }

    @Test
    void testDelete_WhenTodoNull_HaveNull() {
        Todo deletedTodo = todoService.delete(null);
        Assertions.assertNull(deletedTodo);
    }

    private void initDB() {
        todoRepository.deleteAll();

        Todo todo = Todo.builder()
                .id(UUID.randomUUID())
                .titre("titre")
                .description("description")
                .date(LocalDate.now())
                .valid(true)
                .build();

        Todo todo2 = Todo.builder()
                .id(UUID.randomUUID())
                .titre("titre2")
                .description("description2")
                .date(LocalDate.now())
                .valid(false)
                .build();

        Todo todo3 = Todo.builder()
                .id(UUID.randomUUID())
                .titre("titre3")
                .description("description3")
                .date(LocalDate.now())
                .valid(true)
                .build();


        todos.add(todoRepository.save(todo));
        todos.add(todoRepository.save(todo2));
        todos.add(todoRepository.save(todo3));
    }

    private static Todo getTestTodo() {
        String titre = "titre test";
        String description = "description test";
        LocalDate date = LocalDate.now();
        boolean valid = true;

        return Todo.builder()
                .titre(titre)
                .description(description)
                .date(date)
                .valid(valid)
                .build();
    }

}