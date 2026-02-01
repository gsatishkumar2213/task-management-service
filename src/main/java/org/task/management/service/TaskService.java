package org.task.management.service;

import org.springframework.stereotype.Service;
import org.task.management.dto.TaskRequest;
import org.task.management.dto.TaskResponse;
import org.task.management.entity.Task;
import org.task.management.exception.InvalidInputException;
import org.task.management.exception.TaskNotFoundException;
import org.task.management.repo.TaskRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

@Service
public class TaskService {

    private TaskRepository taskRepository;

    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    static TaskResponse entityToDTO(Task task) {
        return new TaskResponse(task.getTaskId(), task.getUserId(), task.getTitle()
                , task.getDescription(), task.getStatus(), task.getCreatedAt(), task.getUpdatedAt(),
                task.getResolvedAt());
    }

    public Mono<TaskResponse> getTask(Long taskId) {
        return taskRepository
                .findById(taskId).map(TaskService::entityToDTO)
                .switchIfEmpty(Mono
                        .error(new TaskNotFoundException
                                ("Task not found: " + taskId)));

    }

    public Flux<TaskResponse> getAllTasks() {
        return taskRepository.findAll().map(TaskService::entityToDTO);
    }

    public Mono<TaskResponse> completeTask(Long taskId) {
        return taskRepository
                .findById(taskId).switchIfEmpty(
                        Mono.error(new TaskNotFoundException(
                                "Task not found: " + taskId)))
                .map(task -> {
                    task.setStatus("COMPLETED");
                    task.setUpdatedAt(LocalDateTime.now());
                    return task;
                }).flatMap(taskRepository::save).map(TaskService::entityToDTO);

    }

    public Mono<TaskResponse> updateTask(Long taskId, TaskRequest taskRequest) {
        return taskRepository
                .findById(taskId)
                .switchIfEmpty(Mono.error(new TaskNotFoundException(
                        "Task not found: " + taskId)))
                .map(task -> {
                    task.setUserId(taskRequest.userId());
                    task.setTitle(taskRequest.title());
                    task.setDescription(taskRequest.description());
                    task.setUpdatedAt(LocalDateTime.now());
                    return task;
                }).flatMap(taskRepository::save).map(TaskService::entityToDTO);
    }

    public Mono<Void> deleteTask(Long taskId) {
        return taskRepository
                .findById(taskId).
                switchIfEmpty(Mono.error(new TaskNotFoundException("Task not found " + taskId)))
                .flatMap(taskRepository::delete);
    }

    public Flux<TaskResponse> getTasksByUserId(String userId) {
        return taskRepository
                .findByUserId(userId)
                .map(TaskService::entityToDTO);
    }

    public Flux<TaskResponse> getTasksByUserIdAndStatus(String userId, String status) {
        return taskRepository
                .findByUserIdAndStatus(userId, status)
                .map(TaskService::entityToDTO);
    }

    public Mono<TaskResponse> createTask(TaskRequest taskRequest) {
        if (taskRequest.userId() == null || taskRequest.userId().isEmpty() ||
                taskRequest.title() == null || taskRequest.title().isEmpty() ||
                taskRequest.description() == null || taskRequest.description().isEmpty()) {
            return Mono.error(new InvalidInputException(
                    "userId, title, and description cannot be empty"
            ));
        }
        Task task = new Task(null, taskRequest.userId(), taskRequest.title(),
                taskRequest.description(),
                "PENDING", LocalDateTime.now(), LocalDateTime.now(), null);
        return taskRepository.save(task).map(TaskService::entityToDTO);

    }
}
