package org.task.management.repo;

import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;
import org.task.management.entity.Task;
import reactor.core.publisher.Flux;

@Repository
public interface TaskRepository extends R2dbcRepository<Task, Long> {
    @Query("SELECT * FROM task WHERE user_id = :userId")
    Flux<Task> findByUserId(String userId);

    @Query("SELECT * FROM task WHERE status = :status")
    Flux<Task> findByStatus(String status);

    @Query("SELECT * FROM task WHERE user_id = :userId AND status = :status")
    Flux<Task> findByUserIdAndStatus(String userId, String status);
}
