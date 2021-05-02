package com.example.faunaapp.MVVM.model;

import com.example.faunaapp.DTO.TaskEntry;

public interface IAddEntryModel {
    void submit(TaskEntry taskEntry, String token);
}
