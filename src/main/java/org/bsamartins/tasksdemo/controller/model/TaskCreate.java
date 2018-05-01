package org.bsamartins.tasksdemo.controller.model;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class TaskCreate {
    @NotNull
    @NotEmpty
    private String description;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
