package com.example.faunaapp.Dagger.ApolloClient;

import dagger.Component;

@Component
public interface ClientApolloComponent {
    ClientApollo getClient();
}
