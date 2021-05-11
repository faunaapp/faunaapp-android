package com.example.faunaapp.MVVM.RoomModel.TaskEntry;

import android.app.Application;
import android.os.AsyncTask;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.apollographql.apollo.ApolloCall;
import com.apollographql.apollo.api.Response;
import com.apollographql.apollo.exception.ApolloException;
import com.apollographql.apollo.request.RequestHeaders;
import com.example.faunaapp.DTO.TaskEntry;
import com.example.faunaapp.EventBusObjects.EntriesEvent;
import com.example.faunaapp.EventBusObjects.EntryEvent;
import com.example.faunaapp.Helpers.Helper;
import com.example.faunaapp.MVVM.RemoteDataSource.RemouteSource.AddTaskEntry.AddTaskEntryRemoteDataSource;
import com.example.faunaapp.MVVM.RoomModel.Database.FaunaappDatabase;
import com.faunaapp.graphql.CreateTaskMutation;
import com.faunaapp.graphql.type.Category;

import org.greenrobot.eventbus.EventBus;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class TaskEntryModel implements ITaskEntryModel {
    private static TaskEntryModel instance;
    private TaskEntryDao taskEntryDao;
    private List<TaskEntry> allTasksEntry;
    private final ExecutorService executorService;

    private TaskEntryModel(Application application) {
        FaunaappDatabase faunaappDatabase = FaunaappDatabase.getInstance(application);
        taskEntryDao = faunaappDatabase.taskEntryDao();
        allTasksEntry = new ArrayList<>();
//        allTasksEntry = new MutableLiveData<>();
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
