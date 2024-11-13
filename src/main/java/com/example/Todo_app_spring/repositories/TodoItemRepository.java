package com.example.Todo_app_spring.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.Todo_app_spring.models.TodoItem;

@Repository

public interface  TodoItemRepository extends JpaRepository<TodoItem, Long> {
    
}
