package com.example.faunaapp.data.Entry;

import androidx.lifecycle.LiveData;

import com.example.faunaapp.data.DTO.TaskEntry;

import java.util.List;

public interface ITaskEntryRepository {
     void insertTaskEntry(TaskEntry taskEntry);
     LiveData<List<TaskEntry>> getAllEntries();
}
