package org.bsamartins.tasksdemo.repository;

import org.bsamartins.tasksdemo.model.persistence.Task;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface TaskRepository extends PagingAndSortingRepository<Task, Long> {
    Iterable<Task> findAllByUserId(long id);
}
