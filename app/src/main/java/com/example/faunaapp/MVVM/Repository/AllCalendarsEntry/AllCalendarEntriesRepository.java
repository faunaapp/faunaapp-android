package com.example.faunaapp.MVVM.Repository.AllCalendarsEntry;

import android.app.Application;

import com.apollographql.apollo.ApolloClient;
import com.example.faunaapp.MVVM.RemoteDataSource.ApolloClient.ClientApollo;
import com.example.faunaapp.MVVM.RemoteDataSource.ApolloClient.ClientApolloComponent;

import com.example.faunaapp.MVVM.RemoteDataSource.ApolloClient.DaggerClientApolloComponent;
import com.example.faunaapp.MVVM.RemoteDataSource.RemouteSource.AllCalendarEntries.AllCalendarEntriesDataSource;
import com.example.faunaapp.MVVM.RemoteDataSource.RemouteSource.AllCalendarEntries.IALLCalendarEntriesDataSource;

public class AllCalendarEntriesRepository implements IAllCalendarEntriesRepository{
    private static ApolloClient apolloClient;
    private IALLCalendarEntriesDataSource allCalendarEntriesDataSource;

    public AllCalendarEntriesRepository(Application application) {
        apolloClient = initializeApolloClient();
        allCalendarEntriesDataSource = new AllCalendarEntriesDataSource(apolloClient);
        //TODO to add a model here
    }

    @Override
    public void getAllEntries(String token) {
        allCalendarEntriesDataSource.getAllEntries(token);
    }

    private ApolloClient initializeApolloClient() {
        ClientApolloComponent component = DaggerClientApolloComponent.create();
        ClientApollo clientApollo = component.getClient();
        return clientApollo.getClient();
    }
}
