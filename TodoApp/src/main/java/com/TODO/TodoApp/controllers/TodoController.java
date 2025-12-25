package com.TODO.TodoApp.controller;

import com.TODO.TodoApp.entity.TodoEntity;
import com.TODO.TodoApp.repository.TodoRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class TodoController {

    private final TodoRepository todoRepository;

    public TodoController(TodoRepository todoRepository) {
        this.todoRepository = todoRepository;
    }

    @GetMapping({"/", "/home"})
    public String home(org.springframework.ui.Model model) {
        model.addAttribute("todos", todoRepository.findAll());
        return "index";
    }

    // ADD TASK
    @PostMapping("/add")
    public String addTodo(@RequestParam String title) {
        TodoEntity todo = TodoEntity.builder()
                .title(title)
                .completed(false)
                .build();

        todoRepository.save(todo);
        return "redirect:/";
    }

    // DELETE TASK
    @GetMapping("/delete/{id}")
    public String deleteTodo(@PathVariable Long id) {
        todoRepository.deleteById(id);
        return "redirect:/";
    }

    // TOGGLE COMPLETE
    @GetMapping("/toggle/{id}")
    public String toggleTodo(@PathVariable Long id) {
        TodoEntity todo = todoRepository.findById(id).orElseThrow();
        todo.setCompleted(!todo.getCompleted());
        todoRepository.save(todo);
        return "redirect:/";
    }
}

