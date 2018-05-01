package org.bsamartins.tasksdemo.controller;

import org.bsamartins.tasksdemo.controller.model.TaskAction;
import org.bsamartins.tasksdemo.controller.model.TaskCreate;
import org.bsamartins.tasksdemo.model.web.Task;
import org.bsamartins.tasksdemo.security.AuthenticatedUser;
import org.bsamartins.tasksdemo.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/tasks")
public class TasksController {

    @Autowired
    private TaskService taskService;

    @GetMapping
    public ResponseEntity<Iterable<Task>> findAll(@AuthenticationPrincipal AuthenticatedUser authUser) {
        return ResponseEntity.ok(taskService.findAllByUser(authUser));
    }

    @PostMapping
    public ResponseEntity<Task> create(@Validated @RequestBody TaskCreate taskCreate,
                                       @AuthenticationPrincipal AuthenticatedUser authUser,
                                       BindingResult bindingResult) throws Exception {

        if(bindingResult.hasErrors()) {
            throw new BindException(bindingResult);
        }

        Task saved = taskService.create(taskCreate, authUser);
        return ResponseEntity.ok(saved);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Task> delete(@PathVariable("id") long id,
                                       @AuthenticationPrincipal AuthenticatedUser authUser) {
        Task existingTask = taskService.findById(id).orElse(null);
        if(existingTask == null || authUser.getId() != existingTask.getUserId()) {
            return ResponseEntity.notFound().build();
        }
        taskService.deleteById(id);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{id}/action")
    public ResponseEntity<Task> create(@PathVariable("id") long id,
                                       @Validated @RequestBody TaskAction taskAction,
                                       @AuthenticationPrincipal AuthenticatedUser authUser,
                                       BindingResult bindingResult) throws Exception {
        if(bindingResult.hasErrors()) {
            throw new BindException(bindingResult);
        }

        Task existingTask = taskService.findById(id).orElse(null);
        if(existingTask == null || authUser.getId() != existingTask.getUserId()) {
            return ResponseEntity.notFound().build();
        }
        Task updatedTask = taskService.taskAction(id, taskAction);
        return ResponseEntity.ok(updatedTask);
    }

}
