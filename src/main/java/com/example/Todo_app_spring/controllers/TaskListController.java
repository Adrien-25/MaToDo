package com.example.Todo_app_spring.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.Todo_app_spring.models.TaskList;
import com.example.Todo_app_spring.models.TodoItem;
import com.example.Todo_app_spring.models.User;
import com.example.Todo_app_spring.services.TaskListService;
import com.example.Todo_app_spring.services.UserService;

import ch.qos.logback.core.model.Model;

@RestController
@RequestMapping("/task-lists")
public class TaskListController {

    @Autowired
    private TaskListService taskListService;

    @Autowired
    private UserService userService;

    // Endpoint pour créer une liste
    @PostMapping
    public ResponseEntity<TaskList> createTaskList(@RequestBody TaskList taskList) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated() && !"anonymousUser".equals(authentication.getPrincipal())) {
            String username = authentication.getName();
            User user = userService.getUserByUsername(username);
            taskList.setUser(user);
        }
        TaskList createdTaskList = taskListService.save(taskList);
        return new ResponseEntity<>(createdTaskList, HttpStatus.CREATED);
    }

    // Endpoint pour récupérer toutes les listes
    @GetMapping
    public ResponseEntity<List<TaskList>> getAllTaskLists() {
        List<TaskList> taskLists = taskListService.findAll();
        return ResponseEntity.ok(taskLists);
    }

    // Méthode pour afficher la liste des tâches d'une liste spécifique
    @GetMapping("/{id}")
    public String getTasksByList(@PathVariable Long id, Model model) {
        TaskList taskList = taskListService.findById(id);
        System.out.println(taskList);

        List<TodoItem> todoItems = taskListService.findTasksByListId(id);
        System.out.println(todoItems);
        // model.addAttribute("todoItems", todoItems);  
        // model.addAttribute("taskLists", taskListService.findAll());  
        return "index";
    }

    // Endpoint pour supprimer une liste par ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTaskList(@PathVariable Long id) {
        taskListService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
