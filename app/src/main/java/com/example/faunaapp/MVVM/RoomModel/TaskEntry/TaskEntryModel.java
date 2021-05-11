package com.example.faunaapp.MVVM.RoomModel.TaskEntry;

import android.app.Application;

import com.example.faunaapp.DTO_and_Room_tables.TaskEntry;
import com.example.faunaapp.EventBusObjects.EntriesEvent;
import com.example.faunaapp.MVVM.RoomModel.Database.FaunaappDatabase;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class TaskEntryModel implements ITaskEntryModel {
    private static TaskEntryModel instance;
    private TaskEntryDao taskEntryDao;
    private final ExecutorService executorService;

    private TaskEntryModel(Application application) {
        FaunaappDatabase faunaappDatabase = FaunaappDatabase.getInstance(application);
        taskEntryDao = faunaappDatabase.taskEntryDao();
        executorService = Executors.newFixedThreadPool(2);
    }

    public static synchronized TaskEntryModel getInstance(Application application) {
        if (instance == null) {
            instance = new TaskEntryModel(application);
        }
        return instance;
    }


    @Override
    public void insertTaskEntry(TaskEntry taskEntry) {
        executorService.execute(() -> {
                    taskEntryDao.insert(taskEntry);
                }
        );
    }


    public void getAllEntries() {
        executorService.execute(() ->
        {
            EntriesEvent event = new EntriesEvent();
            event.setEntries(taskEntryDao.getAllEntries());
            EventBus.getDefault().post(event);
        });
    }


}
