package com.example.faunaapp.MVVM.Model.TaskEntry;

import android.app.Application;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.faunaapp.DTO.TaskEntry;
import com.example.faunaapp.MVVM.Model.Database.FaunaappDatabase;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class TaskEntryModel implements ITaskEntryModel {
    private static TaskEntryModel instance;
    private TaskEntryDao taskEntryDao;
    private MutableLiveData<List<TaskEntry>> allTasksEntry;
    private final ExecutorService executorService;
    public TaskEntryModel(Application application) {
        FaunaappDatabase faunaappDatabase = FaunaappDatabase.getInstance(application);
        taskEntryDao = faunaappDatabase.taskEntryDao();
        allTasksEntry = new MutableLiveData<>();
        executorService = Executors.newFixedThreadPool(2);
    }

    public static synchronized TaskEntryModel getInstance(Application application)
    {
        if(instance == null)
        {
            instance = new TaskEntryModel(application);
        }
        return instance;
    }


    @Override
    public void insertTaskEntry(TaskEntry taskEntry) {
        executorService.execute(()-> taskEntryDao.insert(taskEntry));
    }


    @Override
    public void insertAllTaskEntry(List<TaskEntry> taskEntries) {
    }

    public LiveData<List<TaskEntry>> getAllEntries(){
        return allTasksEntry;
    }


}
