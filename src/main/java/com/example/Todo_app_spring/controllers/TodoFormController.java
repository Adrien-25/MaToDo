package com.example.Todo_app_spring.controllers;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.Todo_app_spring.models.TaskCategory;
import com.example.Todo_app_spring.models.TaskList;
import com.example.Todo_app_spring.models.TodoItem;
import com.example.Todo_app_spring.models.User;
import com.example.Todo_app_spring.services.TaskCategoryService;
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
    private TaskCategoryService taskCategoryService;
    @Autowired
    private TaskListService taskListService;

    @GetMapping("/create-todo")
    public String showCreateForm(TodoItem todoItem) {
        return "new-todo-item";
    }

    @PostMapping("/todo")
    // public String createTodoItem(@Valid @ModelAttribute("todoItem") TodoItem todoItem, BindingResult bindingResult, Model model) {
    public String createTodoItem(@Valid @ModelAttribute("todoItem") TodoItem todoItem, BindingResult bindingResult, @RequestParam( "task_list_id")Long taskListId, @RequestParam(value = "categoryId", required = false) Long categoryId, @RequestParam(value = "dueDate", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dueDate, Model model) {
        // Vérifiez les erreurs de validation
        if (bindingResult.hasErrors()) {
            model.addAttribute("error", "Une erreur s'est produite lors de la validation du formulaire.");
            return "redirect:/"; // Redirigez vers une vue d'erreur ou la page initiale
        }
        // Récupérez l'utilisateur authentifié
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated() && !"anonymousUser".equals(authentication.getPrincipal())) {
            String username = authentication.getName();
            User user = userService.getUserByUsername(username);
            todoItem.setUser(user);
        }
        // Récupérez et associez la catégorie
        if (categoryId != null) {
            TaskCategory category = taskCategoryService.findById(categoryId);
            if (category != null) {
                todoItem.setCategory(category);
            }
        }
        TaskList taskList = taskListService.findById(taskListId);

        todoItem.setDueDate(dueDate);
        todoItem.setTaskList(taskList);
        todoItem.setDescription(todoItem.getDescription());
        todoItem.setStatus(TodoItem.Status.TODO);

        // Enregistrer la tâche
        todoItemService.save(todoItem);
        return "redirect:/";
    }

    @GetMapping("/delete/{id}")
    public String deleteTodoItem(@PathVariable("id") Long id, Model model) {
        TodoItem todoItem = todoItemService
                .getById(id)
                .orElseThrow(() -> new IllegalArgumentException("TodoItem id: " + id + " not found"));
        todoItemService.delete(todoItem);
        return "redirect:/";
    }

    @GetMapping("/edit/{id}")
    public String showUpdateForm(@PathVariable("id") Long id, org.springframework.ui.Model model) {
        TodoItem todoItem = todoItemService
                .getById(id)
                .orElseThrow(() -> new IllegalArgumentException("TodoItem id: " + id + " not found"));

        model.addAttribute("todo", todoItem);
        return "edit-todo-item";
    }

    @PostMapping("/todo/{id}")
    public String updateTodoItem(@PathVariable("id") Long id, @Valid TodoItem todoItem, BindingResult result, Model model) {

        TodoItem item = todoItemService
                .getById(id)
                .orElseThrow(() -> new IllegalArgumentException("TodoItem id: " + id + " not found"));

        // item.setIsComplete(todoItem.getIsComplete());
        item.setDescription(todoItem.getDescription());

        todoItemService.save(item);

        return "redirect:/";
    }

    @PatchMapping("/todo/{id}/toggle")
    public ResponseEntity<String> toggleTodoItem(@PathVariable Long id) {
        // TodoItem todoItem = todoItemService.getById(id);
        Optional<TodoItem> optionalTodoItem = todoItemService.getById(id);

        if (optionalTodoItem == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("TodoItem not found");
        }

        TodoItem todoItem = optionalTodoItem.get();
        // todoItem.setIsComplete(!todoItem.getIsComplete());
        todoItemService.save(todoItem);

        return ResponseEntity.ok("TodoItem updated successfully");
    }
}
