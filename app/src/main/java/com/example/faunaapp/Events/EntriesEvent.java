package com.example.faunaapp.Events;

import com.example.faunaapp.DTO.Entry;

import java.util.ArrayList;
import java.util.List;

public class EntriesEvent {
    private List<Entry> entries;

    public EntriesEvent() {
        entries = new ArrayList<>();
    }

    public void setEntries(List<Entry> entries) {
        this.entries = entries;
    }

    public List<Entry> getEntries() {
        return entries;
    }
}
