package org.bsamartins.tasksdemo.controller.model;

import javax.validation.constraints.NotNull;

public class TaskAction {
    public static enum Action {
        CHECK, UNCHECK
    }

    @NotNull
    private Action action;

    public Action getAction() {
        return action;
    }

    public void setAction(Action action) {
        this.action = action;
    }
}
