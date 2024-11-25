package com.example.Todo_app_spring.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.Todo_app_spring.models.TaskCategory;

public interface TaskCategoryRepository extends JpaRepository<TaskCategory, Long> {
    
}
