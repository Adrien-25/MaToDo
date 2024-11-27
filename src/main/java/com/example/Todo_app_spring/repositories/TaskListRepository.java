package com.example.Todo_app_spring.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.Todo_app_spring.models.TaskList;
import com.example.Todo_app_spring.models.User;

@Repository
public interface TaskListRepository extends JpaRepository<TaskList, Long> {

    List<TaskList> findAllByUser(User user);

}
