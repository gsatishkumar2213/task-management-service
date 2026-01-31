package org.task.management.service;

import org.springframework.stereotype.Service;
import org.task.management.TaskStatus;
import org.task.management.dto.TaskRequest;
import org.task.management.dto.TaskResponse;
import org.task.management.entity.Task;
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
        return taskRepository.findById(taskId).map(TaskService::entityToDTO);
    }

    public Flux<TaskResponse> getAllTasks() {
        return taskRepository.findAll().map(TaskService::entityToDTO);
    }

    public Mono<TaskResponse> completeTask(Long taskId) {
        return taskRepository.findById(taskId).switchIfEmpty(Mono.error(new RuntimeException(
                                     "Task not found" + taskId)))
                             .map(task -> {
                                 task.setStatus(TaskStatus.COMPLETED);
                                 task.setUpdatedAt(LocalDateTime.now());
                                 return task;
                             }).flatMap(taskRepository::save).map(TaskService::entityToDTO);

    }

    public Mono<TaskResponse> updateTask(Long taskId, TaskRequest taskRequest) {
        return taskRepository.findById(taskId)
                             .switchIfEmpty(Mono.error(new RuntimeException(
                                     "Task not found" + taskId)))
                             .map(task -> {
                                 task.setUserId(taskRequest.userId());
                                 task.setTitle(taskRequest.title());
                                 task.setDescription(taskRequest.description());
                                 task.setUpdatedAt(LocalDateTime.now());
                                 return task;
                             }).flatMap(taskRepository::save).map(TaskService::entityToDTO);
    }

    public Mono<Void> deleteTask(Long taskId) {
        return taskRepository.findById(taskId).
                             switchIfEmpty(Mono.error(new RuntimeException("Task not found")))
                             .flatMap(taskRepository::delete);
    }

    public Flux<TaskResponse> getTasksByUserId(String userId) {
        return taskRepository.findByUserId(userId).map(TaskService::entityToDTO);
    }

    public Flux<TaskResponse> getTasksByUserIdAndStatus(String userId, TaskStatus status) {
        return taskRepository.findByUserIdAndStatus(userId, status).map(TaskService::entityToDTO);
    }

    public Mono<TaskResponse> createTask(TaskRequest taskRequest) {
        Task task = new Task(null, taskRequest.userId(), taskRequest.title(),
                taskRequest.description(),
                TaskStatus.PENDING, LocalDateTime.now(), LocalDateTime.now(), null);
        return taskRepository.save(task).map(TaskService::entityToDTO);

    }
}
