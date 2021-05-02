package com.example.faunaapp.Dagger.Room.DAO.Entry;

import androidx.lifecycle.LiveData;

import com.example.faunaapp.DTO.TaskEntry;

import java.util.List;

import javax.inject.Inject;

public class TaskEntryDataSource implements ITaskEntryRepository {
    private TaskEntryDao taskEntryDao;

    @Inject
    public TaskEntryDataSource(TaskEntryDao taskEntryDao) {
        this.taskEntryDao = taskEntryDao;
    }

    @Override
    public void insertTaskEntry(TaskEntry taskEntry) {
        taskEntryDao.insert(taskEntry);
    }

    @Override
    public LiveData<List<TaskEntry>> getAllEntries() {
        return taskEntryDao.getAllEntries();
    }


}
