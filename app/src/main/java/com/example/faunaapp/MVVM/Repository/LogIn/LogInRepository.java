package com.example.faunaapp.MVVM.Repository.LogIn;

import android.app.Application;

import com.apollographql.apollo.ApolloClient;
import com.example.faunaapp.EventBusObjects.TokenEvent;
import com.example.faunaapp.MVVM.RemoteDataSource.ApolloClient.ClientApollo;
import com.example.faunaapp.MVVM.RemoteDataSource.ApolloClient.ClientApolloComponent;
import com.example.faunaapp.MVVM.RemoteDataSource.ApolloClient.DaggerClientApolloComponent;
import com.example.faunaapp.MVVM.RemoteDataSource.RemouteSource.LogIn.ILogInRemoteDataSource;
import com.example.faunaapp.MVVM.RemoteDataSource.RemouteSource.LogIn.LogInRemoteDataSource;
import com.example.faunaapp.MVVM.RoomModel.TaskEntry.ITaskEntryModel;
import com.example.faunaapp.MVVM.RoomModel.TaskEntry.TaskEntryModel;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;


public class LogInRepository implements ILogInRepository {
    private static ApolloClient apolloClient;
    private ILogInRemoteDataSource logInRemoteDataSource;


    public LogInRepository(Application application) {
        apolloClient = initializeApolloClient();
        logInRemoteDataSource = new LogInRemoteDataSource(apolloClient);
    }


    private ApolloClient initializeApolloClient() {
        ClientApolloComponent component = DaggerClientApolloComponent.create();
        ClientApollo clientApollo = component.getClient();
        return clientApollo.getClient();
    }

    @Override
    public boolean logIn(String email, String password) {
        return logInRemoteDataSource.logIn(email, password);
    }
}
