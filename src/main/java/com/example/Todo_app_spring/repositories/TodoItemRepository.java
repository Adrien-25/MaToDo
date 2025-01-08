package com.example.Todo_app_spring.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.Todo_app_spring.models.TaskList;
import com.example.Todo_app_spring.models.TodoItem;
import com.example.Todo_app_spring.models.User;

@Repository

public interface TodoItemRepository extends JpaRepository<TodoItem, Long> {

    List<TodoItem> findAllByUser(User user);

    List<TodoItem> findByTaskListOrderByPositionAsc(TaskList taskList);

    List<TodoItem> findByTaskListIdAndPositionGreaterThanOrderByPositionAsc(Long taskListId, int position);

    @Query("SELECT MAX(t.position) FROM TodoItem t WHERE t.taskList.id = :listId")
    Integer findMaxPositionByListId(@Param("listId") Long listId);
}
