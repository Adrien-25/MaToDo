package com.example.Todo_app_spring.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.Todo_app_spring.models.TodoItem;

@Repository
public interface TaskItemRepository extends JpaRepository<TodoItem, Long> {

    List<TodoItem> findByTaskListId(Long taskListId);
}
