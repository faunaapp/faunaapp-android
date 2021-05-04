package com.example.faunaapp.MVVM.Repository.LogIn;

import android.app.Application;

import com.apollographql.apollo.ApolloClient;
import com.example.faunaapp.Dagger.ApolloClient.ClientApollo;
import com.example.faunaapp.Dagger.ApolloClient.ClientApolloComponent;
import com.example.faunaapp.Dagger.ApolloClient.DaggerClientApolloComponent;
import com.example.faunaapp.MVVM.RemoteDataSource.LogIn.ILogInRemoteDataSource;
import com.example.faunaapp.MVVM.RemoteDataSource.LogIn.LogInRemoteDataSource;


public class LogInRepository implements ILogInRepository {
    private static ApolloClient apolloClient;
    private ILogInRemoteDataSource logInRemoteDataSource;


    public LogInRepository(Application application) {
        apolloClient = initializeApolloClient();
        logInRemoteDataSource = new LogInRemoteDataSource(apolloClient);
       //TODO To make Room here
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
