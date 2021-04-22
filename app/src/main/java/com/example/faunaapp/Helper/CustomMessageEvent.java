package com.example.faunaapp.Helper;

import com.example.faunaapp.DTO.Entry;

public class CustomMessageEvent {
    private Entry entry;

    public void setEntry(Entry entry) {
        this.entry = entry;
    }

    public Entry getEntry() {
        return entry;
    }
}
