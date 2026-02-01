package org.task.management.controller;

import org.springframework.web.bind.annotation.*;
import org.task.management.dto.TaskRequest;
import org.task.management.dto.TaskResponse;
import org.task.management.service.TaskService;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/tasks")
public class TaskController {

    private TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @PostMapping
    public Mono<TaskResponse> create(@RequestBody TaskRequest taskRequest) {
        return taskService.createTask(taskRequest);
    }

    @GetMapping("/{taskId}")
    public Mono<TaskResponse> findById(@PathVariable Long taskId) {
        return taskService.getTask(taskId);
    }

    @GetMapping("/user/{userId}")
    public Flux<TaskResponse> findByUserId(@PathVariable String userId) {
        return taskService.getTasksByUserId(userId);
    }

    @GetMapping
    public Flux<TaskResponse> findAll() {
        return taskService.getAllTasks();
    }

    @GetMapping("/user/{userId}/status/{status}")
    public Flux<TaskResponse> findByUserIdAndStatus(@PathVariable String userId,
                                                    @PathVariable String status) {
        return taskService
                .getTasksByUserIdAndStatus(userId, status);
    }

    @DeleteMapping("/{taskId}")
    public Mono<Void> deleteTask(@PathVariable Long taskId) {
        return taskService.deleteTask(taskId);
    }

    @PutMapping("/{taskId}")
    public Mono<TaskResponse> updateTask(@RequestBody TaskRequest taskRequest,
                                         @PathVariable Long taskId) {
        return taskService.updateTask(taskId, taskRequest);
    }
}
