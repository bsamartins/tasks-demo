package org.bsamartins.tasksdemo.service;

import org.bsamartins.tasksdemo.controller.model.TaskAction;
import org.bsamartins.tasksdemo.controller.model.TaskCreate;
import org.bsamartins.tasksdemo.model.persistence.Task;
import org.bsamartins.tasksdemo.repository.TaskRepository;
import org.bsamartins.tasksdemo.repository.UserRepository;
import org.bsamartins.tasksdemo.security.AuthenticatedUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.ValidationException;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class TaskService {

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private UserRepository userRepository;

    public Task create(TaskCreate taskCreate, AuthenticatedUser authUser) {
        LocalDateTime now = LocalDateTime.now();
        Task task = new Task();
        task.setDescription(taskCreate.getDescription());
        task.setUserId(authUser.getId());
        task.setTimestampCreated(now);
        task.setTimestampUpdated(now);
        validateTask(task);
        return taskRepository.save(task);
    }

    public Iterable<Task> findAllByUser(AuthenticatedUser authUser) {
        return taskRepository.findAllByUserId(authUser.getId());
    }

    public Optional<Task> findById(long id) {
        return taskRepository.findById(id);
    }

    public void deleteById(long id) {
        taskRepository.deleteById(id);
    }

    public Task taskAction(long id, TaskAction taskAction) {
        Task task  = taskRepository.findById(id).get();
        task.setChecked(taskAction.getAction() == TaskAction.Action.CHECK);
        task.setTimestampUpdated(LocalDateTime.now());
        return taskRepository.save(task);
    }

    private void validateTask(Task task) {
        if(!userRepository.existsById(task.getUserId())) {
            throw new ValidationException("User not found exception: " + task.getUserId());
        }
    }
}
