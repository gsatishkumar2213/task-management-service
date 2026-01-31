package org.task.management.dto;

public record TaskRequest(String userId,
                          String title,
                          String description) {
}
