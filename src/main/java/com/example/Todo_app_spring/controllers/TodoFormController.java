package com.example.Todo_app_spring.controllers;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.Todo_app_spring.models.TaskList;
import com.example.Todo_app_spring.models.TodoItem;
import com.example.Todo_app_spring.models.User;
import com.example.Todo_app_spring.services.TaskListService;
import com.example.Todo_app_spring.services.TodoItemService;
import com.example.Todo_app_spring.services.UserService;

import jakarta.validation.Valid;

@Controller
public class TodoFormController {

    @Autowired
    private TodoItemService todoItemService;

    @Autowired
    private UserService userService;

    @Autowired
    private TaskListService taskListService;

    @GetMapping("/create-todo")
    public String showCreateForm(TodoItem todoItem) {
        return "new-todo-item";
    }

    @PostMapping("/todo")
    public String createTodoItem(
            @Valid @ModelAttribute("todoItem") TodoItem todoItem,
            BindingResult bindingResult,
            @RequestParam("task_list_id") Long taskListId,
            @RequestParam(value = "dueDate", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dueDate,
            Model model) {
        // Vérifiez les erreurs de validation
        if (bindingResult.hasErrors()) {
            model.addAttribute("error", "Une erreur s'est produite lors de la validation du formulaire.");
            return "redirect:/";
        }
        // Récupérez l'utilisateur authentifié
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated() && !"anonymousUser".equals(authentication.getPrincipal())) {
            String username = authentication.getName();
            User user = userService.getUserByUsername(username);
            todoItem.setUser(user);
        }
        TaskList taskList = taskListService.findById(taskListId);

        todoItem.setDueDate(dueDate);
        todoItem.setTaskList(taskList);
        todoItem.setDescription(todoItem.getDescription());
        todoItem.setStatus(TodoItem.Status.TODO);

        // Integer maxPosition = todoItemRepository.findMaxPositionByListId(todoItem.getList().getId());
        // todoItem.setPosition((maxPosition != null ? maxPosition : 0) + 1);
        todoItemService.save(todoItem);
        return "redirect:/task-lists/" + taskListId;

    }

    // DEPLACER UNE TACHE
    @PostMapping("/move/{id}")
    // @PatchMapping("/move/{id}")
    // public ResponseEntity<Void> moveTask(
    public String moveTask(
            @PathVariable("id") Long taskId,
            @RequestParam("task_list_id") Long taskListId,
            @RequestParam int newPosition) {

        todoItemService.moveTask(taskId, taskListId, newPosition);
        return "redirect:/";
    }

    // SUPPRIMER UNE TACHE
    @DeleteMapping("/delete/{id}")
    public String deleteTodoItem(@PathVariable Long id, @RequestParam("task_list_id") Long taskListId) {
        TodoItem todoItem = todoItemService
                .getById(id)
                .orElseThrow(() -> new IllegalArgumentException("TodoItem id: " + id + " not found"));
        todoItemService.delete(todoItem);
        return "redirect:/task-lists/" + taskListId;
    }

    @PostMapping("/todo/{id}")
    public String updateTodoItem(
            @Valid @ModelAttribute("todoItem") TodoItem todoItem,
            @PathVariable("id") Long id,
            BindingResult result,
            @RequestParam("task_list_id") Long taskListId,
            Model model
    ) {
        if (result.hasErrors()) {
            model.addAttribute("error", "Validation error. Please check the input data.");
            return "redirect:/";
        }

        TodoItem existingItem = todoItemService
                .getById(id)
                .orElseThrow(() -> new IllegalArgumentException("TodoItem id: " + id + " not found"));

        if (todoItem.getDescription() != null && !todoItem.getDescription().isBlank()) {
            existingItem.setDescription(todoItem.getDescription());
        }

        todoItemService.save(existingItem);

        // return "redirect:/task-lists/" + taskListId;
        return "redirect:/";
    }

    // STATUT DE TACHE FAITE / A FAIRE
    @PatchMapping("/todo/{id}/toggle")
    public String toggleTodoItem(
            @PathVariable Long id,
            @RequestParam("task_list_id") Long taskListId
    ) {
        Optional<TodoItem> optionalTodoItem = todoItemService.getById(id);

        TodoItem todoItem = optionalTodoItem.get();
        if (todoItem.getStatus() == TodoItem.Status.TODO) {
            todoItem.setStatus(TodoItem.Status.DONE);
        } else if (todoItem.getStatus() == TodoItem.Status.DONE) {
            todoItem.setStatus(TodoItem.Status.TODO);
        }

        todoItemService.save(todoItem);

        return "redirect:/task-lists/" + taskListId;

    }
}
