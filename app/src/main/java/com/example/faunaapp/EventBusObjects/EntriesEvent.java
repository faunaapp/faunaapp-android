package com.example.faunaapp.EventBusObjects;

import com.example.faunaapp.data.DTO.TaskEntry;

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
