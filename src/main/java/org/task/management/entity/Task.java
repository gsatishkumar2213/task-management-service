package org.task.management.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;
import org.task.management.TaskStatus;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Table("task")
public class Task {
    @Id
    @Column("task_id")
    Long taskId;
    @Column("user_id")
    String userId;
    String title;
    String description;
    TaskStatus status;
    @Column("created_at")
    LocalDateTime createdAt;
    @Column("updated_at")
    LocalDateTime updatedAt;
    @Column("resolved_at")
    LocalDateTime resolvedAt;
}
