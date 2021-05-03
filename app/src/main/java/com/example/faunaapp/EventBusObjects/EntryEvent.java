package com.example.faunaapp.EventBusObjects;

import com.example.faunaapp.data.DTO.TaskEntry;

public class EntryEvent {
    private TaskEntry taskEntry;

    public EntryEvent() {
        taskEntry = new TaskEntry();
    }

    public TaskEntry getTaskEntry() {
        return taskEntry;
    }

    public void setTaskEntry(TaskEntry taskEntry) {
        this.taskEntry = taskEntry;
    }

}
