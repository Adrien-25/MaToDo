package com.example.Todo_app_spring.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.Todo_app_spring.models.TodoItem;
import com.example.Todo_app_spring.models.User;

@Repository

public interface TodoItemRepository extends JpaRepository<TodoItem, Long> {

    List<TodoItem> findAllByUser(User user);

}
