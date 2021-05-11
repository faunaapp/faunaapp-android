package com.example.faunaapp.MVVM.Repository.AllCalendarsEntry;

import android.app.Application;

import com.apollographql.apollo.ApolloClient;
import com.example.faunaapp.MVVM.RemoteDataSource.ApolloClient.ClientApollo;
import com.example.faunaapp.MVVM.RemoteDataSource.ApolloClient.ClientApolloComponent;

import com.example.faunaapp.MVVM.RemoteDataSource.ApolloClient.DaggerClientApolloComponent;
import com.example.faunaapp.MVVM.RemoteDataSource.RemouteSource.AllCalendarEntries.AllCalendarEntriesDataSource;
import com.example.faunaapp.MVVM.RemoteDataSource.RemouteSource.AllCalendarEntries.IALLCalendarEntriesDataSource;
import com.example.faunaapp.MVVM.RoomModel.TaskEntry.ITaskEntryModel;
import com.example.faunaapp.MVVM.RoomModel.TaskEntry.TaskEntryModel;

public class AllCalendarEntriesRepository implements IAllCalendarEntriesRepository{
    private static ApolloClient apolloClient;
    private IALLCalendarEntriesDataSource allCalendarEntriesDataSource;
    private ITaskEntryModel taskEntryModel;

    public AllCalendarEntriesRepository(Application application) {
        apolloClient = initializeApolloClient();
        allCalendarEntriesDataSource = new AllCalendarEntriesDataSource(apolloClient);
        taskEntryModel = TaskEntryModel.getInstance(application);
        //TODO to add a model here
    }

    @Override
    public void getAllEntries(String token) {
        //taskEntryModel.getAllEntries();
        allCalendarEntriesDataSource.getAllEntries(token);
    }

    private ApolloClient initializeApolloClient() {
        ClientApolloComponent component = DaggerClientApolloComponent.create();
        ClientApollo clientApollo = component.getClient();
        return clientApollo.getClient();
    }
}
