package com.example.Todo_app_spring.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.Todo_app_spring.models.TaskList;

public interface TaskListRepository extends JpaRepository<TaskList, Long> {
    
}
