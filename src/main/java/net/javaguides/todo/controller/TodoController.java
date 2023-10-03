package net.javaguides.todo.controller;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import net.javaguides.todo.dto.TodoDto;
import net.javaguides.todo.service.TodoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("api/todos")
@AllArgsConstructor
public class TodoController {

    private TodoService todoService;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<TodoDto> addTodo(@RequestBody @Valid TodoDto todoDto) {
        TodoDto savedTodo = todoService.addTodo(todoDto);
        return new ResponseEntity<>(savedTodo, HttpStatus.CREATED);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @GetMapping("{todoId}")
    public ResponseEntity<TodoDto> getTodo(@PathVariable("todoId") Long todoId) {
        TodoDto foundTodoDto = todoService.getTodo(todoId);
        return ResponseEntity.ok(foundTodoDto);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @GetMapping
    public ResponseEntity<List<TodoDto>> getAllTodos() {
        return ResponseEntity.ok(todoService.getAllTodos());
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("{todoId}")
    public ResponseEntity<TodoDto> updateTodo(@PathVariable("todoId") Long todoId, @RequestBody TodoDto updatedTodo) {
        return ResponseEntity.ok(todoService.updateTodo(todoId, updatedTodo));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("{todoId}")
    public ResponseEntity<String> deleteTodo(@PathVariable("todoId") Long todoId) {
        todoService.deleteTodo(todoId);
        return ResponseEntity.ok("Todo was successfully deleted");
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @PatchMapping("{todoId}/complete")
    public ResponseEntity<TodoDto> completeTodo(@PathVariable("todoId") Long todoId) {
        return ResponseEntity.ok(todoService.completeTodo(todoId));
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @PatchMapping("{todoId}/incomplete")
    public ResponseEntity<TodoDto> incompleteTodo(@PathVariable("todoId") Long todoId) {
        return ResponseEntity.ok(todoService.incompleteTodo(todoId));
    }

}
