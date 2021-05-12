package com.example.faunaapp.EventBusObjects;

import com.example.faunaapp.DTO_and_Room_tables.TaskEntry;

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
