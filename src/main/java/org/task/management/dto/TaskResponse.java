package org.task.management.dto;

import java.time.LocalDateTime;

public record TaskResponse(Long taskId,
                           String userId,
                           String title,
                           String description,
                           String status,
                           LocalDateTime createdAt,
                           LocalDateTime updatedAt,
                           LocalDateTime resolvedAt) {
}
