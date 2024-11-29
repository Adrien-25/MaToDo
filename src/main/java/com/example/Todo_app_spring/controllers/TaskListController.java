package com.example.Todo_app_spring.controllers;

import java.util.Comparator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.Todo_app_spring.models.TaskList;
import com.example.Todo_app_spring.models.TodoItem;
import com.example.Todo_app_spring.models.User;
import com.example.Todo_app_spring.services.TaskListService;
import com.example.Todo_app_spring.services.UserService;

@Controller
@RequestMapping("/task-lists")
public class TaskListController {

    @Autowired
    private TaskListService taskListService;

    @Autowired
    private UserService userService;

    // Endpoint pour créer une liste
    @PostMapping
    public String createTaskList(@RequestParam("name") String name) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        User user = userService.getUserByUsername(username);

        TaskList taskList = new TaskList();
        taskList.setName(name);
        taskList.setUser(user);
        TaskList createdTaskList = taskListService.save(taskList);

        return "redirect:/task-lists/" + createdTaskList.getId();

    }

    // Méthode pour afficher la liste des tâches d'une liste spécifique
    @GetMapping("/{id}")
    public String getTasksByList(@PathVariable Long id, Model model) {
        // Username 
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        User user = userService.getUserByUsername(username);

        // Listes de l'user
        List<TaskList> userTaskLists = taskListService.getAllByUser(user);

        TaskList taskList = taskListService.findById(id);

        List<TodoItem> todoItems = taskListService.findTasksByListId(id);
        todoItems.sort(Comparator.comparing(TodoItem::getStatus));

        model.addAttribute("selectedTaskList", taskList);
        model.addAttribute("selectedTaskListId", id);
        model.addAttribute("todoItems", todoItems);
        model.addAttribute("taskLists", userTaskLists);
        model.addAttribute("username", username);

        return "index";
    }

    // Endpoint pour supprimer une liste par ID
    @DeleteMapping("/{id}")
    public String deleteTaskList(@PathVariable Long id) {
        taskListService.deleteById(id);
        return "redirect:/"; // Redirige vers la page d'accueil pour recharger la liste mise à jour
    }

}
