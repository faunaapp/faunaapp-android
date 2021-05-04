package com.example.faunaapp.MVVM.RemoteDataSource.AddTaskEntry;

import com.example.faunaapp.DTO.TaskEntry;

public interface IAddTaskEntryRemoteDataSource {
    void submit(TaskEntry taskEntry, String token);
}
