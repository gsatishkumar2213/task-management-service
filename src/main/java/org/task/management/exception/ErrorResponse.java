package org.task.management.exception;

public record ErrorResponse(
        int status,
        String message,
        long timestamp
) {
}