package net.javaguides.todo.service;

import lombok.AllArgsConstructor;
import net.javaguides.todo.dto.TodoDto;
import net.javaguides.todo.entity.Todo;
import net.javaguides.todo.exception.ResourceNotFoundException;
import net.javaguides.todo.repository.TodoRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class TodoServiceImpl implements TodoService {

    private TodoRepository todoRepository;
    private ModelMapper modelMapper;

    @Override
    public TodoDto addTodo(TodoDto todoDto) {

        Todo savedTodo = todoRepository.save(modelMapper.map(todoDto, Todo.class));

        return modelMapper.map(savedTodo, TodoDto.class);
    }

    @Override
    public TodoDto getTodo(Long todoId) {
        return todoRepository.findById(todoId)
                .map(todo -> modelMapper.map(todo, TodoDto.class))
                .orElseThrow(() -> new ResourceNotFoundException("Todo with ID " + todoId + " not found"));
    }

    @Override
    public List<TodoDto> getAllTodos() {
        return todoRepository.findAll()
                .stream()
                .map(todo -> modelMapper.map(todo, TodoDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public TodoDto updateTodo(Long todoId, TodoDto updatedTodoDto) {
        Todo foundTodo = todoRepository.findById(todoId)
                .orElseThrow(() -> new ResourceNotFoundException("Todo with ID " + todoId + " not found"));

        foundTodo.setTitle(updatedTodoDto.getTitle());
        foundTodo.setDescription(updatedTodoDto.getDescription());
        foundTodo.setCompleted(updatedTodoDto.isCompleted());

        Todo savedTodo = todoRepository.save(foundTodo);

        return modelMapper.map(savedTodo, TodoDto.class);
    }

    @Override
    public void deleteTodo(Long todoId) {
        Todo foundTodo = todoRepository.findById(todoId)
                .orElseThrow(() -> new ResourceNotFoundException("Todo with ID " + todoId + " not found"));

        todoRepository.deleteById(todoId);
    }

    @Override
    public TodoDto completeTodo(Long todoId) {
        Todo foundTodo = todoRepository.findById(todoId)
                .orElseThrow(() -> new ResourceNotFoundException("Todo with ID " + todoId + " not found"));

        foundTodo.setCompleted(true);

        Todo savedTodo = todoRepository.save(foundTodo);

        return modelMapper.map(savedTodo, TodoDto.class);
    }

    @Override
    public TodoDto incompleteTodo(Long todoId) {
        Todo foundTodo = todoRepository.findById(todoId)
                .orElseThrow(() -> new ResourceNotFoundException("Todo with ID " + todoId + " not found"));

        foundTodo.setCompleted(false);

        Todo savedTodo = todoRepository.save(foundTodo);

        return modelMapper.map(savedTodo, TodoDto.class);
    }
}
