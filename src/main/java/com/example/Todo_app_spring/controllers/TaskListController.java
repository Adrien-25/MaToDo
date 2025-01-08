package com.example.Todo_app_spring.controllers;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
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

        DateTimeFormatter dayMonthFormatter = DateTimeFormatter.ofPattern("d MMM");
        int currentYear = LocalDate.now().getYear();

        for (TodoItem item : todoItems) {
            LocalDate dueDate = item.getDueDate();
            if (dueDate != null) {
                try {
                    String formattedDate = dueDate.format(dayMonthFormatter);
                    if (dueDate.getYear() != currentYear) {
                        formattedDate += " " + dueDate.getYear();
                    }
                    item.setFormattedDueDate(formattedDate);
                    System.out.println("Date formatée : " + formattedDate);
                } catch (DateTimeParseException e) {
                    System.err.println("Erreur lors du parsing de la date : " + e.getMessage());
                }
            }
        }
        User currentUser = userService.getCurrentUser();

        // todoItems.sort(Comparator.comparing(TodoItem::getStatus));
        todoItems.sort(Comparator
                .comparing(TodoItem::getStatus) // Priorité : Status
                .thenComparing(Comparator.comparing(TodoItem::getPosition).reversed()));

        String selectedtaskListname = taskList.getName();

        model.addAttribute("selectedTaskList", taskList);
        model.addAttribute("selectedTaskListId", id);
        model.addAttribute("selectedtaskListname", selectedtaskListname);
        model.addAttribute("todoItems", todoItems);
        model.addAttribute("taskLists", userTaskLists);
        model.addAttribute("username", username);
        model.addAttribute("userInfo", currentUser);

        return "index";
    }

    @PostMapping("/{id}")
    public String updateTaskListName(
            @PathVariable("id") Long id,
            @RequestParam("name") String newName,
            @RequestParam("task_list_id") Long taskListId,
            Model model
    ) {
        // Vérifier si la liste existe
        TaskList taskList = taskListService.findById(id);
        // .orElseThrow(() -> new IllegalArgumentException("TaskList id: " + id + " not found"));

        // Mettre à jour le nom de la liste
        taskList.setName(newName);
        taskListService.save(taskList);

        return "redirect:/task-lists/" + taskListId;
        // return "redirect:/";
    }

    // Endpoint pour supprimer une liste par ID
    @DeleteMapping("/{id}")
    public String deleteTaskList(@PathVariable Long id) {
        taskListService.deleteById(id);
        return "redirect:/"; // Redirige vers la page d'accueil pour recharger la liste mise à jour
    }

}
