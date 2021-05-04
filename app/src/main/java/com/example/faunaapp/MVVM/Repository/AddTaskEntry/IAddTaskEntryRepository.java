package com.example.faunaapp.MVVM.Repository.AddTaskEntry;

import com.example.faunaapp.DTO.TaskEntry;

public interface IAddTaskEntryRepository {
    void submit(TaskEntry taskEntry, String token);
}
