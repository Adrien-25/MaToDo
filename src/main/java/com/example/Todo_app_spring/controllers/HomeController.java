package com.example.Todo_app_spring.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import com.example.Todo_app_spring.models.TaskList;
import com.example.Todo_app_spring.models.User;
import com.example.Todo_app_spring.services.TaskListService;
import com.example.Todo_app_spring.services.UserService;

@Controller
public class HomeController {

    // @Autowired
    // private TodoItemService todoItemService;
    @Autowired
    private TaskListService taskListService;

    @Autowired
    private UserService userService;

    @GetMapping("/")
    public ModelAndView index() {
        ModelAndView modelAndView = new ModelAndView("index");

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.isAuthenticated() && !"anonymousUser".equals(authentication.getPrincipal())) {
            String username = authentication.getName();

            User user = userService.getUserByUsername(username);
            User currentUser = userService.getCurrentUser();

            List<TaskList> userTaskLists = taskListService.getAllByUser(user);

            // modelAndView.addObject("todoItems", userTodoItems);
            modelAndView.addObject("taskLists", userTaskLists);
            modelAndView.addObject("username", username);
            modelAndView.addObject("userInfo", currentUser);
        }

        return modelAndView;
    }

}
