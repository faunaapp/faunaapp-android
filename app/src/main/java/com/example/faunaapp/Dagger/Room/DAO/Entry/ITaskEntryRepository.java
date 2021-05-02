package com.example.faunaapp.Dagger.Room.DAO.Entry;

import androidx.lifecycle.LiveData;

import com.example.faunaapp.DTO.TaskEntry;

import java.util.List;

public interface ITaskEntryRepository {
     void insertTaskEntry(TaskEntry taskEntry);
     LiveData<List<TaskEntry>> getAllEntries();
}
