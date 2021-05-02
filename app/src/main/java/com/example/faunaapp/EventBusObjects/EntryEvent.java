package com.example.faunaapp.EventBusObjects;

import com.example.faunaapp.DTO.Entry;

public class EntryEvent {
    private Entry entry;

    public EntryEvent() {
        entry = new Entry();
    }

    public Entry getEntry() {
        return entry;
    }

    public void setEntry(Entry entry) {
        this.entry = entry;
    }

}
