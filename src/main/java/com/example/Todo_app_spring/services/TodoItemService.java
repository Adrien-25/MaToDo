package com.example.Todo_app_spring.services;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.Todo_app_spring.models.TodoItem;
import com.example.Todo_app_spring.models.User;
import com.example.Todo_app_spring.repositories.TodoItemRepository;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

@Service
public class TodoItemService {

    @Autowired
    private TodoItemRepository todoItemRepository;

    public Iterable<TodoItem> getAll() {
        return todoItemRepository.findAll();
    }

    public Optional<TodoItem> getById(Long id) {
        return todoItemRepository.findById(id);
    }

    public TodoItem save(TodoItem todoItem) {
        if (todoItem.getId() == null) {
            todoItem.setCreatedAt(Instant.now());
        }
        todoItem.setUpdatedAt(Instant.now());
        return todoItemRepository.save(todoItem);
    }

    public void delete(TodoItem todoItem) {
        todoItemRepository.delete(todoItem);
    }

    public List<TodoItem> getAllByUser(User user) {
        return todoItemRepository.findAllByUser(user);
    }

     @Transactional
    public void moveTask(Long taskId, Long listId, int newPosition) {
        // Récupérer la tâche à déplacer
        TodoItem taskToMove = todoItemRepository.findById(taskId)
                .orElseThrow(() -> new EntityNotFoundException("Task not found"));

        // Récupérer toutes les tâches de la liste triées par position
        List<TodoItem> tasks = todoItemRepository.findByListIdOrderByPositionAsc(listId);

        // Supprimer la tâche existante
        tasks.removeIf(task -> task.getId().equals(taskId));

        // Ajouter la tâche à la nouvelle position
        tasks.add(newPosition, taskToMove);

        // Réassigner les positions
        for (int i = 0; i < tasks.size(); i++) {
            tasks.get(i).setPosition(i + 1);
        }

        // Sauvegarder les modifications
        todoItemRepository.saveAll(tasks);
    }
}
