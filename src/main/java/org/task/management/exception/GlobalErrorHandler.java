package org.task.management.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalErrorHandler {

    @ExceptionHandler(TaskNotFoundException.class)
    public ResponseEntity<ErrorResponse>
    handlerTaskNotFound(TaskNotFoundException e) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(new ErrorResponse(404,
                        e.getMessage(), System.currentTimeMillis()));
    }

    @ExceptionHandler(InvalidInputException.class)
    public ResponseEntity<ErrorResponse> handlerInvalidInput(InvalidInputException e) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ErrorResponse(400, e.getMessage(),
                        System.currentTimeMillis()));
    }

    @ExceptionHandler(DatabaseConnectionException.class)
    public ResponseEntity<ErrorResponse> handlerDatabaseConnection(DatabaseConnectionException e) {
        return ResponseEntity.
                status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ErrorResponse(500,
                        "Internal server error: " + e.getMessage()
                        , System.currentTimeMillis()));
    }
}
