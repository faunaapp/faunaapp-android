package com.example.faunaapp.MVVM.Repository.AddTaskEntry;

import com.example.faunaapp.DTO_and_Room_tables.TaskEntry;

public interface IAddTaskEntryRepository {
    void submit(TaskEntry taskEntry, String token);
}
