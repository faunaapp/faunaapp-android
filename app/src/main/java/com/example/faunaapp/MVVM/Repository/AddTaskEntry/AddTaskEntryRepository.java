package com.example.faunaapp.MVVM.Repository.AddTaskEntry;

import android.app.Application;

import com.apollographql.apollo.ApolloClient;
import com.example.faunaapp.DTO.TaskEntry;
import com.example.faunaapp.EventBusObjects.TokenEvent;
import com.example.faunaapp.MVVM.RemoteDataSource.ApolloClient.ClientApollo;
import com.example.faunaapp.MVVM.RemoteDataSource.ApolloClient.ClientApolloComponent;

import com.example.faunaapp.MVVM.RemoteDataSource.ApolloClient.DaggerClientApolloComponent;
import com.example.faunaapp.MVVM.RoomModel.TaskEntry.ITaskEntryModel;
import com.example.faunaapp.MVVM.RoomModel.TaskEntry.TaskEntryModel;
import com.example.faunaapp.MVVM.RemoteDataSource.RemouteSource.AddTaskEntry.AddTaskEntryRemoteDataSource;
import com.example.faunaapp.MVVM.RemoteDataSource.RemouteSource.AddTaskEntry.IAddTaskEntryRemoteDataSource;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.List;

public class AddTaskEntryRepository implements IAddTaskEntryRepository {

    private static ApolloClient apolloClient;
    private IAddTaskEntryRemoteDataSource addTaskEntryRemoteDataSource;
    private ITaskEntryModel taskEntryModel;


    public AddTaskEntryRepository(Application application) {
        apolloClient = initializeApolloClient();
        addTaskEntryRemoteDataSource = new AddTaskEntryRemoteDataSource(apolloClient);
        taskEntryModel = TaskEntryModel.getInstance(application);
    }



    private ApolloClient initializeApolloClient() {
        ClientApolloComponent component = DaggerClientApolloComponent.create();
        ClientApollo clientApollo = component.getClient();
        return clientApollo.getClient();
    }


    @Override
    public void submit(TaskEntry taskEntry, String token) {
        taskEntryModel.insertTaskEntry(taskEntry);
        addTaskEntryRemoteDataSource.submit(taskEntry, token);
    }
}
