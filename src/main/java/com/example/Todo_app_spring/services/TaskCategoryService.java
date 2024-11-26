package com.example.Todo_app_spring.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.Todo_app_spring.models.TaskCategory;
import com.example.Todo_app_spring.repositories.TaskCategoryRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class TaskCategoryService {

    @Autowired
    private TaskCategoryRepository taskCategoryRepository;

    public TaskCategory findById(Long id) {
        return taskCategoryRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Catégorie introuvable pour l'id : " + id));
    }

}
