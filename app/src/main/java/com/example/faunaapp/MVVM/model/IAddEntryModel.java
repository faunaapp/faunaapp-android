package com.example.faunaapp.MVVM.model;

import com.example.faunaapp.data.DTO.TaskEntry;

public interface IAddEntryModel {
    void submit(TaskEntry taskEntry, String token);
}
