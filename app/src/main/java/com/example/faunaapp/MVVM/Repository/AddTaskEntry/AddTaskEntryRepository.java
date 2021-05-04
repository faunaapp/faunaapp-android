package com.example.faunaapp.MVVM.Repository.AddTaskEntry;

import android.app.Application;

import com.apollographql.apollo.ApolloClient;
import com.example.faunaapp.DTO.TaskEntry;
import com.example.faunaapp.Dagger.ApolloClient.ClientApollo;
import com.example.faunaapp.Dagger.ApolloClient.ClientApolloComponent;
import com.example.faunaapp.Dagger.ApolloClient.DaggerClientApolloComponent;
import com.example.faunaapp.MVVM.Model.TaskEntry.ITaskEntryModel;
import com.example.faunaapp.MVVM.Model.TaskEntry.TaskEntryModel;
import com.example.faunaapp.MVVM.RemoteDataSource.AddTaskEntry.AddTaskEntryRemoteDataSource;
import com.example.faunaapp.MVVM.RemoteDataSource.AddTaskEntry.IAddTaskEntryRemoteDataSource;

public class AddTaskEntryRepository implements IAddTaskEntryRepository {

    private static ApolloClient apolloClient;
    private IAddTaskEntryRemoteDataSource addTaskEntryRemoteDataSource;
    private ITaskEntryModel taskTaskEntryModel;


    public AddTaskEntryRepository(Application application) {
        apolloClient = initializeApolloClient();
        addTaskEntryRemoteDataSource = new AddTaskEntryRemoteDataSource(apolloClient);
        taskTaskEntryModel = new TaskEntryModel(application);
    }

    private ApolloClient initializeApolloClient() {
        ClientApolloComponent component = DaggerClientApolloComponent.create();
        ClientApollo clientApollo = component.getClient();
        return clientApollo.getClient();
    }


    @Override
    public void submit(TaskEntry taskEntry, String token) {
        taskTaskEntryModel.insertTaskEntry(taskEntry);
        addTaskEntryRemoteDataSource.submit(taskEntry, token);
    }
}
