package org.task.management.dto;

import org.task.management.TaskStatus;

import java.time.LocalDateTime;

public record TaskResponse(Long taskId,
                           String userId,
                           String title,
                           String description,
                           TaskStatus status,
                           LocalDateTime createdAt,
                           LocalDateTime updatedAt,
                           LocalDateTime resolvedAt) {
}
