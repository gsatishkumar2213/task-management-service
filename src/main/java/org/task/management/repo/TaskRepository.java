package org.task.management.repo;

import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;
import org.task.management.TaskStatus;
import org.task.management.entity.Task;
import reactor.core.publisher.Flux;

@Repository
public interface TaskRepository extends R2dbcRepository<Task, Long> {
    @Query("Select * from task where user_id =:userId")
    Flux<Task> findByUserId(String userId);

    @Query("select * from task where status =:status")
    Flux<Task> findByStatus(TaskStatus status);

    @Query("select * from task where user_id=:userId AND status=:status")
    Flux<Task> findByUserIdAndStatus(String userId, TaskStatus status);
}
