package com.example.faunaapp.MVVM.RemoteDataSource.RemouteSource.AddTaskEntry;

import com.example.faunaapp.DTO_and_Room_tables.TaskEntry;

public interface IAddTaskEntryRemoteDataSource {
    void submit(TaskEntry taskEntry, String token);
}
