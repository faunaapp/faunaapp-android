package com.example.faunaapp.EventBusObjects;

import com.example.faunaapp.DTO_and_Room_tables.TaskEntry;

import java.util.ArrayList;
import java.util.List;

public class EntriesEvent {
    private List<TaskEntry> entries;

    public EntriesEvent() {
        entries = new ArrayList<>();
    }

    public void setEntries(List<TaskEntry> entries) {
        this.entries = entries;
    }

    public List<TaskEntry> getEntries() {
        return entries;
    }
}
