package com.example.Todo_app_spring.models;

import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "todo_items")
public class TodoItem implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;

    @Column(length = 500)
    private String description;

    private LocalDate dueDate;
    private String formattedDueDate;


    private Instant createdAt;

    private Instant updatedAt;

    @Enumerated(EnumType.STRING)
    private Status status = Status.TODO;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "task_list_id", nullable = false)
    private TaskList taskList;

    @Override
    public String toString() {
        return String.format(
                "TodoItem{id=%d, name='%s', description='%s', status='%s', createdAt='%s', updatedAt='%s', dueDate='%s', user_id=%d, taskList_id=%d}",
                id, name, description, status, createdAt, updatedAt, dueDate,
                user != null ? user.getId() : null,
                taskList != null ? taskList.getId() : null
        );
    }

    public enum Status {
        TODO,
        IN_PROGRESS,
        DONE,
        DELETED
    }

    // Getters et setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Instant updatedAt) {
        this.updatedAt = updatedAt;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public TaskList getTaskList() {
        return taskList;
    }

    public void setTaskList(TaskList taskList) {
        this.taskList = taskList;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }
    public void setFormattedDueDate(String formattedDueDate) {
        this.formattedDueDate = formattedDueDate;
    }

    public String getFormattedDueDate() {
        return formattedDueDate;
    }
}
