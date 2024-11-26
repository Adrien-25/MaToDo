package com.example.Todo_app_spring.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.Todo_app_spring.models.TaskList;
import com.example.Todo_app_spring.models.TodoItem;
import com.example.Todo_app_spring.models.User;
import com.example.Todo_app_spring.repositories.TaskItemRepository;
import com.example.Todo_app_spring.repositories.TaskListRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class TaskListService {

    @Autowired
    private TaskListRepository taskListRepository;

    @Autowired
    private TaskItemRepository taskItemRepository;

    // Créer une nouvelle liste
    public TaskList save(TaskList taskList) {
        return taskListRepository.save(taskList);
    }

    // Récupérer toutes les listes
    public List<TaskList> findAll() {
        return taskListRepository.findAll();
    }

    // Récupérer une liste par son ID
    public TaskList findById(Long id) {
        return taskListRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Liste introuvable pour l'id : " + id));
    }

    // Supprimer une liste par son ID
    public void deleteById(Long id) {
        if (!taskListRepository.existsById(id)) {
            throw new EntityNotFoundException("Impossible de supprimer : liste introuvable pour l'id : " + id);
        }
        taskListRepository.deleteById(id);
    }

    // Mettre à jour une liste existante
    public TaskList updateTaskList(Long id, TaskList updatedTaskList) {
        TaskList existingTaskList = findById(id);
        existingTaskList.setName(updatedTaskList.getName());
        existingTaskList.setTasks(updatedTaskList.getTasks());
        return taskListRepository.save(existingTaskList);
    }

    public List<TodoItem> findTasksByListId(Long listId) {
        return taskItemRepository.findByTaskListId(listId);
    }

    public List<TaskList> getAllByUser(User user) {
        return taskListRepository.findAllByUser(user);
    }
}
