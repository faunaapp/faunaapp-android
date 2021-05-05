package com.example.faunaapp.MVVM.RemoteDataSource.ApolloClient;

import dagger.Component;

@Component
public interface ClientApolloComponent {
    ClientApollo getClient();
}
