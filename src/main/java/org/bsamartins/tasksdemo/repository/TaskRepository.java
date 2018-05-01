package org.bsamartins.tasksdemo.repository;

import org.bsamartins.tasksdemo.model.web.Task;
import org.bsamartins.tasksdemo.model.web.User;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface TaskRepository extends PagingAndSortingRepository<Task, Long> {
    Iterable<Task> findAllByUserId(long id);
}
